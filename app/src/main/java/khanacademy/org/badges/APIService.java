package khanacademy.org.badges;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import khanacademy.org.badges.data.KADB;
import khanacademy.org.badges.data.model.BadgeModel;
import khanacademy.org.badges.data.model.CategoryModel;
import khanacademy.org.badges.data.orm.BadgeORM;
import khanacademy.org.badges.data.orm.CategoryORM;

public class APIService extends IntentService {
    private static final String TAG = "APIService";

    public static final String ACTION_GET_BADGES = "action_get_badges";
    public static final String ACTION_GET_BADGE_CATEGORIES = "action_get_badge_categories";

    public static final String UPDATE_BADGES = "update_badges";
    public static final String UPDATE_BADGE_CATEGORIES = "update_badge_categories";

    public APIService() { super(TAG); }

    @Override
    protected void onHandleIntent( Intent intent ) {
        try {
            synchronized( TAG ) {
                String strAction = intent.getAction();
                Log.v( TAG, "Handling intent: "+strAction);
                if( strAction.equals( ACTION_GET_BADGES ) ) {
                    if( API_badges() ) LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(UPDATE_BADGES));
                }
                if( strAction.equals( ACTION_GET_BADGE_CATEGORIES ) ) {
                    if( API_badges_categories() ) LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(UPDATE_BADGE_CATEGORIES));
                }
            }
        } catch( Exception err ) {
            Log.e(TAG, "APIService failed", err);
        }
    }

    private JSONArray API_simple_get( String endPoint ) throws JSONException, IOException {
        JSONArray jsonResponse = null;
        final OkHttpClient httpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url( String.format( "%s%s", getString(R.string.api_url), endPoint ) )
                .build();

        final Response response = httpClient.newCall(request).execute();
        if( !response.isSuccessful() ) throw new IOException( String.format( "Unexpected code: %s", response ) );
        jsonResponse = new JSONArray( response.body().string() );
        return jsonResponse;
    }

    private boolean API_badges() {
        boolean bReturn = false;
        final KADB dbHelper = new KADB( this );
        Log.d( TAG, "Sending badges GET" );

        try {

            JSONArray jsonResponse = API_simple_get( "/badges" );
            Log.v( TAG, jsonResponse.toString() );

            List<BadgeModel> raModels = new ArrayList<>();
            for( int i = 0; i < jsonResponse.length(); i++ ) {
                raModels.add( new BadgeModel(jsonResponse.getJSONObject(i)) );
            }

            final BadgeORM orm = new BadgeORM( dbHelper.getWritableDatabase() );
            orm.deleteWhere( null );
            orm.saveAll( raModels );

            bReturn = true;

        } catch( IOException | JSONException err ) {
            Log.e( TAG, "API_badges failed: " + ( err != null ? err.getMessage() : "Unknown" ), err );
        } finally {
            if( dbHelper != null ) dbHelper.close();
        }

        return bReturn;
    }

    private boolean API_badges_categories() {
        boolean bReturn = false;
        final KADB dbHelper = new KADB( this );
        Log.d( TAG, "Sending badges/categories GET" );

        try {

            JSONArray jsonResponse = API_simple_get( "/badges/categories" );
            Log.v( TAG, jsonResponse.toString() );

            List<CategoryModel> raModels = new ArrayList<>();
            for( int i = 0; i < jsonResponse.length(); i++ ) {
                raModels.add( new CategoryModel(jsonResponse.getJSONObject(i)) );
            }

            final CategoryORM orm = new CategoryORM( dbHelper.getWritableDatabase() );
            orm.saveAll( raModels );

            bReturn = true;

        } catch( IOException | JSONException err ) {
            Log.e( TAG, "API_badges_categories failed: " + ( err != null ? err.getMessage() : "Unknown" ), err );
        } finally {
            if( dbHelper != null ) dbHelper.close();
        }

        return bReturn;
    }
}
