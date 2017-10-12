package nl.yzaazy.androidairport.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import nl.yzaazy.androidairport.Model.Airport;

public class AirportsDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "airports.sqlite";
    private static final int DATABASE_VERSION = 1;

    public AirportsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getAirportsByIso(String iso_country){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT icao, name FROM airports WHERE iso_country = '" + iso_country + "' ORDER BY name";
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

    public Airport getAirportByIcao(String icao){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM airports WHERE icao = '" + icao + "'";
        Cursor c = db.rawQuery(query, null);
        Airport airport = new Airport();
        if(c.moveToFirst()){
            airport.setIcao(c.getString(c.getColumnIndex("icao")));
            airport.setName(c.getString(c.getColumnIndex("name")));
            airport.setLongitude(c.getDouble(c.getColumnIndex("longitude")));
            airport.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
            airport.setElevation(c.getDouble(c.getColumnIndex("elevation")));
            airport.setIso_country(c.getString(c.getColumnIndex("iso_country")));
            airport.setMunicipality(c.getString(c.getColumnIndex("municipality")));
        }
        return airport;
    }
}
