package khanacademy.org.badges.data.loader;

import android.content.AsyncTaskLoader;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import khanacademy.org.badges.data.KADB;
import khanacademy.org.badges.data.orm.BadgeORM;
import khanacademy.org.badges.APIService;

public class BadgeLoader extends AsyncTaskLoader<Cursor> {
    private static final String TAG = "BadgeLoader";

    private final BadgeORM mORM;
    private final LocalBroadcastManager mBroadcastManager;
    private final BroadcastReceiver mObserver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "BROADCAST RECEIVED");
            if( intent.getAction().equals(APIService.UPDATE_BADGES) ) onContentChanged();
        }
    };

    private Cursor mData;
    private boolean mRemoteRequestSent = false;

    public BadgeLoader( Context context ) {
        super(context);
        final KADB dbHelper = new KADB( context );
        this.mORM = new BadgeORM( dbHelper.getReadableDatabase() );
        this.mBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @Override
    public Cursor loadInBackground() {
        return this.mORM.getCursorAll();
    }

    @Override
    public void deliverResult( Cursor data ) {
        if( isReset() ) {
            if( data != null ) releaseResources(data);
            return;
        }

        Cursor oldData = mData;
        mData = data;

        if( isStarted() ) {
            super.deliverResult(data);

            Log.d(TAG, "STARTING SERVICE?");
            if( !mRemoteRequestSent ) {
                mRemoteRequestSent = true;
                getContext().startService( new Intent(APIService.ACTION_GET_BADGES, null, getContext(), APIService.class) );
                getContext().startService( new Intent(APIService.ACTION_GET_BADGE_CATEGORIES, null, getContext(), APIService.class) );
            }
        }
        if( oldData != null && oldData != data ) releaseResources(oldData);
    }

    @Override
    protected void onStartLoading() {
        if( mData != null ) deliverResult(mData);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(APIService.UPDATE_BADGES);

        this.mBroadcastManager.registerReceiver( mObserver, intentFilter );

        if( takeContentChanged() || mData == null ) forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();

        if( mData != null ) {
            releaseResources(mData);
            mData = null;
        }

        this.mBroadcastManager.unregisterReceiver( mObserver );
    }

    @Override
    public void onCanceled( Cursor data ) {
        super.onCanceled(data);
        if( data != null ) releaseResources(data);
    }

    private void releaseResources( Cursor data ) {
        data.close();
    }
}
