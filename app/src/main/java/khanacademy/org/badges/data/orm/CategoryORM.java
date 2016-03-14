package khanacademy.org.badges.data.orm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import icenerd.com.data.ORM;
import khanacademy.org.badges.data.model.CategoryModel;

public class CategoryORM extends ORM<CategoryModel> {

    public static final String TABLE_NAME = "category";

    public static final String COL_CATEGORY = "category";
    public static final String COL_ICON_SRC = "icon_src";
    public static final String COL_TYPE_LABEL = "type_label";
    public static final String COL_EMAIL_ICON = "email_icon_src";
    public static final String COL_LARGE_ICON_SRC = "large_icon_src";
    public static final String COL_COMPACT_ICON_SRC = "compact_icon_src";
    public static final String COL_TRANSLATED_DESCRIPTION = "translated_description";
    public static final String COL_CHART_ICON_SRC = "chart_icon_src";
    public static final String COL_MEDIUM_ICON_SRC = "medium_icon_src";
    public static final String COL_DESCRIPTION = "description";

    public CategoryORM( SQLiteDatabase db ) {
        super( db, TABLE_NAME );
    }

    @Override
    protected CategoryModel build( Cursor cursor ) {
        return new CategoryModel(cursor);
    }

}
