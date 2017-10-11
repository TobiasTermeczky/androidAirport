package nl.yzaazy.androidairport.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class AirportsDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "airports.sqlite";
    private static final int DATABASE_VERSION = 1;

    public AirportsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getAirportsByIso(String iso_country){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT icao, name FROM airports WHERE iso_country = '" + iso_country + "'";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c;
    }

    public Cursor getCountryGrouped(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT iso_country FROM airports GROUP BY iso_country;";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return c;
    }
}
