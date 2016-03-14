package khanacademy.org.badges.data.orm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import icenerd.com.data.ORM;
import khanacademy.org.badges.data.model.BadgeModel;

public class BadgeORM extends ORM<BadgeModel> {

    public static final String TABLE_NAME = "badge";

    public static final String COL_ICON_SRC = "icon_src";
    public static final String COL_HIDE_CONTEXT = "hide_context";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_RELATIVE_URL = "relative_url";
    public static final String COL_ICON_SMALL = "icon_small";
    public static final String COL_ICON_COMPACT = "icon_compact";
    public static final String COL_ICON_LARGE = "icon_large";
    public static final String COL_ICON_EMAIL = "icon_email";
    public static final String COL_ABSOLUTE_URL = "absolute_url";
    //unused? public static final String COL_USER_BADGES = "user_badges";
    public static final String COL_TRANSLATED_SAFE_EXTENDED_DESCRIPTION = "translated_safe_extended_description";
    public static final String COL_TRANSLATED_DESCRIPTION = "translated_description";
    public static final String COL_IS_OWNED = "is_owned";
    public static final String COL_BADGE_CATEGORY = "badge_category";
    public static final String COL_POINTS = "points";
    public static final String COL_IS_RETIRED = "is_retired";
    public static final String COL_SAFE_EXTENDED_DESCRIPTION = "safe_extended_description";
    public static final String COL_SLUG = "slug";
    public static final String COL_NAME = "name";

    public BadgeORM( SQLiteDatabase db ) {
        super( db, TABLE_NAME );
    }

    @Override
    protected BadgeModel build( Cursor cursor ) {
        return new BadgeModel( cursor );
    }

}
