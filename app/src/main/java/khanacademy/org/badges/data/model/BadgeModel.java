package khanacademy.org.badges.data.model;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import icenerd.com.data.Model;
import khanacademy.org.badges.data.orm.BadgeORM;

public class BadgeModel extends Model {

    public String icon_src;
    public boolean hide_context;
    public String description;
    public String relative_url;

    public String icon_small;
    public String icon_compact;
    public String icon_large;
    public String icon_email;

    public String absolute_url;
    //unsed? public *unknown type* user_badges;
    public String translated_safe_extended_description;
    public String translated_description;
    public boolean is_owned;
    public int badge_category; // ref to TABLE_CATEGORY -> id
    public int points;
    public boolean is_retired;
    public String safe_extended_description;
    public String slug;
    public String _name; // "name" is taken by ide

    public BadgeModel( Cursor cursor ) {
        icon_src = cursor.getString(cursor.getColumnIndex(BadgeORM.COL_ICON_SRC));
        hide_context = (cursor.getInt(cursor.getColumnIndex(BadgeORM.COL_HIDE_CONTEXT))==0?false:true);
        description = cursor.getString(cursor.getColumnIndex(BadgeORM.COL_DESCRIPTION));
        relative_url = cursor.getString(cursor.getColumnIndex(BadgeORM.COL_RELATIVE_URL));

        icon_small = cursor.getString(cursor.getColumnIndex(BadgeORM.COL_ICON_SMALL));
        icon_compact = cursor.getString(cursor.getColumnIndex(BadgeORM.COL_ICON_COMPACT));
        icon_large = cursor.getString(cursor.getColumnIndex(BadgeORM.COL_ICON_LARGE));
        icon_email = cursor.getString(cursor.getColumnIndex(BadgeORM.COL_ICON_EMAIL));

        absolute_url = cursor.getString(cursor.getColumnIndex(BadgeORM.COL_ABSOLUTE_URL));
        //unsed? public *unknown type* user_badges;
        translated_safe_extended_description = cursor.getString(cursor.getColumnIndex(BadgeORM.COL_TRANSLATED_SAFE_EXTENDED_DESCRIPTION));
        translated_description = cursor.getString(cursor.getColumnIndex(BadgeORM.COL_TRANSLATED_DESCRIPTION));
        is_owned = (cursor.getInt(cursor.getColumnIndex(BadgeORM.COL_IS_OWNED))==0?false:true);
        badge_category = cursor.getInt(cursor.getColumnIndex(BadgeORM.COL_BADGE_CATEGORY));
        points = cursor.getInt(cursor.getColumnIndex(BadgeORM.COL_POINTS));
        is_retired = (cursor.getInt(cursor.getColumnIndex(BadgeORM.COL_IS_RETIRED))==0?false:true);
        safe_extended_description = cursor.getString(cursor.getColumnIndex(BadgeORM.COL_SAFE_EXTENDED_DESCRIPTION));
        slug = cursor.getString(cursor.getColumnIndex(BadgeORM.COL_SLUG));
        _name = cursor.getString(cursor.getColumnIndex(BadgeORM.COL_NAME));
    }

    public BadgeModel( JSONObject json ) throws JSONException {
        icon_src = (json.has(BadgeORM.COL_ICON_SRC)?json.getString(BadgeORM.COL_ICON_SRC):"");
        hide_context = (json.has(BadgeORM.COL_HIDE_CONTEXT)?json.getBoolean(BadgeORM.COL_HIDE_CONTEXT):false);
        description = (json.has(BadgeORM.COL_DESCRIPTION)?json.getString(BadgeORM.COL_DESCRIPTION):"");
        relative_url = (json.has(BadgeORM.COL_RELATIVE_URL)?json.getString(BadgeORM.COL_RELATIVE_URL):"");

        {
            JSONObject jsonIcons = json.getJSONObject("icons"); //caution: "icons" expected
            icon_small = (jsonIcons.has("small") ? jsonIcons.getString("small") : "");
            icon_compact = (jsonIcons.has("compact") ? jsonIcons.getString("compact") : "");
            icon_large = (jsonIcons.has("large") ? jsonIcons.getString("large") : "");
            icon_email = (jsonIcons.has("email") ? jsonIcons.getString("email") : "");
        }

        absolute_url = (json.has(BadgeORM.COL_ABSOLUTE_URL)?json.getString(BadgeORM.COL_ABSOLUTE_URL):"");
        //unsued? user_badges = /*unknown type*/;
        translated_safe_extended_description = (json.has(BadgeORM.COL_TRANSLATED_SAFE_EXTENDED_DESCRIPTION)?json.getString(BadgeORM.COL_TRANSLATED_SAFE_EXTENDED_DESCRIPTION):"");
        translated_description = (json.has(BadgeORM.COL_TRANSLATED_DESCRIPTION)?json.getString(BadgeORM.COL_TRANSLATED_DESCRIPTION):"");
        is_owned = (json.has(BadgeORM.COL_IS_OWNED)?json.getBoolean(BadgeORM.COL_IS_OWNED):false);
        badge_category = (json.has(BadgeORM.COL_BADGE_CATEGORY)?json.getInt(BadgeORM.COL_BADGE_CATEGORY):null);
        points = (json.has(BadgeORM.COL_POINTS)?json.getInt(BadgeORM.COL_POINTS):0);
        is_retired = (json.has(BadgeORM.COL_IS_RETIRED)?json.getBoolean(BadgeORM.COL_IS_RETIRED):false);
        safe_extended_description = (json.has(BadgeORM.COL_SAFE_EXTENDED_DESCRIPTION)?json.getString(BadgeORM.COL_SAFE_EXTENDED_DESCRIPTION):"");
        slug = (json.has(BadgeORM.COL_SLUG)?json.getString(BadgeORM.COL_SLUG):"");
        _name = (json.has(BadgeORM.COL_NAME)?json.getString(BadgeORM.COL_NAME):"");
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put( BadgeORM.COL_ICON_SRC, icon_src );
        values.put( BadgeORM.COL_HIDE_CONTEXT, hide_context );
        values.put( BadgeORM.COL_DESCRIPTION, description );
        values.put( BadgeORM.COL_RELATIVE_URL, relative_url );
        values.put( BadgeORM.COL_ICON_SMALL, icon_small );
        values.put( BadgeORM.COL_ICON_COMPACT, icon_compact );
        values.put( BadgeORM.COL_ICON_LARGE, icon_large );
        values.put( BadgeORM.COL_ICON_EMAIL, icon_email );
        values.put( BadgeORM.COL_ABSOLUTE_URL, absolute_url );
        //unused? values.put( BadgeORM.COL_USER_BADGES, user_badges );
        values.put( BadgeORM.COL_TRANSLATED_SAFE_EXTENDED_DESCRIPTION, translated_safe_extended_description );
        values.put( BadgeORM.COL_TRANSLATED_DESCRIPTION, translated_description );
        values.put( BadgeORM.COL_IS_OWNED, (is_owned?1:0) );
        values.put( BadgeORM.COL_BADGE_CATEGORY, badge_category );
        values.put( BadgeORM.COL_POINTS, points );
        values.put( BadgeORM.COL_IS_RETIRED, (is_retired?1:0) );
        values.put( BadgeORM.COL_SAFE_EXTENDED_DESCRIPTION, safe_extended_description );
        values.put( BadgeORM.COL_SLUG, slug );
        values.put( BadgeORM.COL_NAME, _name );

        return values;
    }
}
