package khanacademy.org.badges.data.model;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import icenerd.com.data.Model;
import khanacademy.org.badges.data.orm.CategoryORM;

public class CategoryModel extends Model {

    private int category;

    public String icon_src;
    public String type_label;
    public String email_icon;
    public String large_icon_src;
    public String compact_icon_src;
    public String translated_description;
    public String chart_icon_src;
    public String medium_icon_src;
    public String description;

    public CategoryModel( Cursor cursor ) {
        category = cursor.getInt( cursor.getColumnIndex(CategoryORM.COL_CATEGORY) );

        icon_src = cursor.getString(cursor.getColumnIndex(CategoryORM.COL_ICON_SRC));
        type_label = cursor.getString(cursor.getColumnIndex(CategoryORM.COL_TYPE_LABEL));
        email_icon = cursor.getString(cursor.getColumnIndex(CategoryORM.COL_EMAIL_ICON));
        large_icon_src = cursor.getString(cursor.getColumnIndex(CategoryORM.COL_LARGE_ICON_SRC));
        compact_icon_src = cursor.getString(cursor.getColumnIndex(CategoryORM.COL_COMPACT_ICON_SRC));
        translated_description = cursor.getString(cursor.getColumnIndex(CategoryORM.COL_TRANSLATED_DESCRIPTION));
        chart_icon_src = cursor.getString(cursor.getColumnIndex(CategoryORM.COL_CHART_ICON_SRC));
        medium_icon_src = cursor.getString(cursor.getColumnIndex(CategoryORM.COL_MEDIUM_ICON_SRC));
        description = cursor.getString(cursor.getColumnIndex(CategoryORM.COL_DESCRIPTION) );
    }

    public CategoryModel( JSONObject json ) throws JSONException {
        category = json.getInt( CategoryORM.COL_CATEGORY ); // JSONException if id not found

        icon_src = (json.has(CategoryORM.COL_ICON_SRC)?json.getString(CategoryORM.COL_ICON_SRC):"");
        type_label = (json.has(CategoryORM.COL_TYPE_LABEL)?json.getString(CategoryORM.COL_TYPE_LABEL):"");
        email_icon = (json.has(CategoryORM.COL_EMAIL_ICON)?json.getString(CategoryORM.COL_EMAIL_ICON):"");
        large_icon_src = (json.has(CategoryORM.COL_LARGE_ICON_SRC)?json.getString(CategoryORM.COL_LARGE_ICON_SRC):"");
        compact_icon_src = (json.has(CategoryORM.COL_COMPACT_ICON_SRC)?json.getString(CategoryORM.COL_COMPACT_ICON_SRC):"");
        translated_description = (json.has(CategoryORM.COL_TRANSLATED_DESCRIPTION)?json.getString(CategoryORM.COL_TRANSLATED_DESCRIPTION):"");
        chart_icon_src = (json.has(CategoryORM.COL_CHART_ICON_SRC)?json.getString(CategoryORM.COL_CHART_ICON_SRC):"");
        medium_icon_src = (json.has(CategoryORM.COL_MEDIUM_ICON_SRC)?json.getString(CategoryORM.COL_MEDIUM_ICON_SRC):"");
        description = (json.has(CategoryORM.COL_DESCRIPTION)?json.getString(CategoryORM.COL_DESCRIPTION):"");
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put( CategoryORM.COL_CATEGORY, category );
        values.put( CategoryORM.COL_ICON_SRC, icon_src );
        values.put( CategoryORM.COL_TYPE_LABEL, type_label );
        values.put( CategoryORM.COL_EMAIL_ICON, email_icon );
        values.put( CategoryORM.COL_LARGE_ICON_SRC, large_icon_src );
        values.put( CategoryORM.COL_COMPACT_ICON_SRC, compact_icon_src );
        values.put( CategoryORM.COL_TRANSLATED_DESCRIPTION, translated_description );
        values.put( CategoryORM.COL_CHART_ICON_SRC, chart_icon_src );
        values.put( CategoryORM.COL_MEDIUM_ICON_SRC, medium_icon_src );
        values.put( CategoryORM.COL_DESCRIPTION, description );


        return values;
    }
}
