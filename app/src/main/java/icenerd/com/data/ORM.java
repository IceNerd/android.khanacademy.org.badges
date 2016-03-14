package icenerd.com.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import khanacademy.org.badges.BuildConfig;

public abstract class ORM<T extends Model> {
    private static final String TAG = "ORM";

    protected final SQLiteDatabase DB;
    public final String TABLE_NAME;

    protected ORM(SQLiteDatabase db, String strTableName) {
        DB = db;
        TABLE_NAME = strTableName;
    }

    protected abstract T build( Cursor cursor );

    public T findWhere( String whereCond ) {
        T model = null;

        final String sqlQuery = String.format( "SELECT * FROM %s WHERE %s", TABLE_NAME, whereCond );
        Cursor cursor = DB.rawQuery( sqlQuery, null );

        if( cursor.getCount() > 0 ) {
            cursor.moveToFirst();
            model = build( cursor );
        } else {
            if(BuildConfig.DEBUG) Log.v(TAG, String.format("Could not find: %s", whereCond));
        }

        return model;
    }

    public Cursor getCursorAll() { return getCursorAll( null ); }
    public Cursor getCursorAll( String whereCond ) {
        final String sqlQuery;
        if( whereCond == null ) {
            sqlQuery = String.format( "SELECT * FROM %s", TABLE_NAME );
        } else {
            sqlQuery = String.format( "SELECT * FROM %s WHERE %s", TABLE_NAME, whereCond );
        }
        return DB.rawQuery( sqlQuery, null );
    }

    public List<T> getAll() { return getAll( null ); }
    public List<T> getAll( String whereCond ) {
        List<T> raModels = new ArrayList<T>();
        Cursor cursor = getCursorAll( whereCond );

        if( cursor.getCount() > 0 ) {
            cursor.moveToFirst();
            while( !cursor.isAfterLast() ) {
                raModels.add( build( cursor ) );
                cursor.moveToNext();
            }
        } else {
            if(BuildConfig.DEBUG) Log.v( TAG, String.format( "Could not get all: %s", whereCond ) );
        }

        return raModels;
    }

    public int saveAll( List<T> raModels ) {
        int iReturn = 0;
        for( T model : raModels  ) {
            iReturn += saveAll(model);
        }
        return iReturn;
    }
    public int saveAll( T model ) {
        return ( save(model) ? 1 : 0 );
    }

    public boolean save( List<T> raModels ) {
        boolean bReturn = true;

        for( T model : raModels ) {
            if( !save(model) ) bReturn = false;
        }

        return bReturn;
    }
    public boolean save( T model ) {
        if( model == null ) return false;
        boolean bReturn = false;

        final ContentValues values = model.getContentValues();
        final long dbReturn = DB.insertWithOnConflict( TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE );
        if( dbReturn > 0 ) {
            if(BuildConfig.DEBUG) Log.v( TAG, "INSERTED   @" + TABLE_NAME + String.format( "(%s)", dbReturn ) );
            bReturn = true;
        } else {
            if(BuildConfig.DEBUG) Log.e( TAG, "INSERT FAIL@" + TABLE_NAME );
        }

        return bReturn;
    }

    public int deleteWhere( String whereCond ) {
        final int iReturn = DB.delete( TABLE_NAME, whereCond, null );

        if( iReturn > 0 ) {
            if(BuildConfig.DEBUG) Log.v( TAG, "DELETED    @" + TABLE_NAME + String.format("(%s records)", iReturn) );
        } else {
            if(BuildConfig.DEBUG) Log.e( TAG, "DELETEMISS?@" + TABLE_NAME + String.format("(%s)%s", iReturn, whereCond) );
        }

        return iReturn;
    }

}
