package khanacademy.org.badges.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import khanacademy.org.badges.data.orm.BadgeORM;
import khanacademy.org.badges.data.orm.CategoryORM;

public class KADB extends SQLiteOpenHelper {

    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS %s";

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "khanacademy";

    public KADB( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION, new DatabaseErrorHandler() {
            @Override
            public void onCorruption(SQLiteDatabase dbObj) {
                Log.e("DB", "ERROR");
            }
        });
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        String sql;

        sql="CREATE TABLE "+ BadgeORM.TABLE_NAME+" ( " +
                "icon_src TEXT,"+
                "hide_context INTEGER,"+
                "description TEXT,"+
                "relative_url TEXT,"+
                "icon_small TEXT,"+
                "icon_compact TEXT,"+
                "icon_large TEXT,"+
                "icon_email TEXT,"+
                "absolute_url TEXT,"+
                //unused? "user_badges null,"+
                "translated_safe_extended_description TEXT,"+
                "translated_description TEXT,"+
                "is_owned INTEGER,"+
                "badge_category INTEGER,"+
                "points INTEGER,"+
                "is_retired INTEGER,"+
                "safe_extended_description TEXT,"+
                "slug TEXT,"+
                "name TEXT"+
                ")";
        db.execSQL( sql );
        sql="CREATE TABLE "+ CategoryORM.TABLE_NAME+" ( " +
                "category INTEGER, " +
                "icon_src TEXT,"+
                "type_label TEXT,"+
                "email_icon_src TEXT,"+
                "large_icon_src TEXT,"+
                "compact_icon_src TEXT,"+
                "translated_description TEXT,"+
                "chart_icon_src TEXT,"+
                "medium_icon_src TEXT,"+
                "description TEXT"+
                ")";
        db.execSQL( sql );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( String.format( DROP_TABLE_IF_EXISTS, BadgeORM.TABLE_NAME ) );
        db.execSQL( String.format( DROP_TABLE_IF_EXISTS, CategoryORM.TABLE_NAME ) );
        this.onCreate(db);
    }
}
