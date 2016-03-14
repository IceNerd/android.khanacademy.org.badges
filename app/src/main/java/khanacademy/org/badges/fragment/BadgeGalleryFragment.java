package khanacademy.org.badges.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.Random;

import icenerd.com.adapter.CursorRecyclerAdapter;
import khanacademy.org.badges.R;
import khanacademy.org.badges.data.loader.BadgeLoader;
import khanacademy.org.badges.data.model.BadgeModel;

public class BadgeGalleryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "BadgeGalleryFragment";
    private static final int LOADER_ID=new Random().nextInt();

    private View mFragmentView;
    private RecyclerView mRecyclerView;
    private BadgeAdapter mAdapter;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        if( savedInstanceState == null ) {
            mFragmentView = inflater.inflate( R.layout.fragment_badge_gallery, container, false );
            mRecyclerView = (RecyclerView) mFragmentView.findViewById(R.id.recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        return mFragmentView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);

        mRequestQueue = Volley.newRequestQueue( getActivity() );
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String,Bitmap> mCache = new LruCache<>(50);
            public void putBitmap( String url, Bitmap bitmap ) {
                mCache.put(url,bitmap);
            }
            public Bitmap getBitmap( String url ) {
                return mCache.get(url);
            }
        });

        mAdapter = new BadgeAdapter();
        mRecyclerView.setAdapter(mAdapter);
        getActivity().getLoaderManager().initLoader( LOADER_ID, null, this ).forceLoad();
    }

    @Override
    public Loader<Cursor> onCreateLoader( int id, Bundle args) {
        if( id == LOADER_ID ) return new BadgeLoader( getActivity() );
        return null;
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor data ) { mAdapter.swapCursor(data); }

    @Override
    public void onLoaderReset( Loader<Cursor> loader ) { mAdapter.changeCursor(null); }

    public void startBadgeDialog( BadgeModel model ) {
        FragmentManager fm = getFragmentManager();
        BadgeDetailDialogFragment dialog = BadgeDetailDialogFragment.newInstance(model,mImageLoader);
        dialog.show( fm, "dialog_badge" );
        dialog.setCancelable(false);
    }

    private class BadgeAdapter extends CursorRecyclerAdapter<BadgeAdapter.ViewHolder> {

        public BadgeAdapter() {
            super(null);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_badge, parent, false );
            return new ViewHolder( itemView );
        }

        @Override
        public void onBindViewHolder( ViewHolder holder, int position ) {
            final BadgeModel model = new BadgeModel( getItem(position) );

            holder.container.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View view ) {
                    startBadgeDialog(model);
                }
            });

            holder.netImageView.setImageUrl( model.icon_compact, mImageLoader );

        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View container;
            public NetworkImageView netImageView;

            public ViewHolder( View itemView ) {
                super(itemView);
                container = itemView.findViewById(R.id.container);
                netImageView = (NetworkImageView) itemView.findViewById(R.id.thumbnail);
            }
        }
    }
}
