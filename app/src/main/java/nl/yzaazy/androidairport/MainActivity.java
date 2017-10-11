package nl.yzaazy.androidairport;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.yzaazy.androidairport.Adapter.ExpandableListAdapter;
import nl.yzaazy.androidairport.Helper.AirportsDatabase;
import nl.yzaazy.androidairport.Model.Airport;
import nl.yzaazy.androidairport.R;

public class MainActivity extends AppCompatActivity {

    AirportsDatabase adb;
    ExpandableListAdapter listAdapter;
    ExpandableListView expandableListView;
    List<String> listDataGroup;
    HashMap<String, List<Airport>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adb = new AirportsDatabase(getApplicationContext());
        expandableListView = (ExpandableListView) findViewById(R.id.lvExpandableListView);
        getDataFromDatabase();

        listAdapter = new ExpandableListAdapter(this, listDataGroup, listDataChild);
        expandableListView.setAdapter(listAdapter);
    }

    private void getDataFromDatabase() {
        listDataGroup = new ArrayList<>();
        listDataChild = new HashMap<>();

        Cursor groupCursor = adb.getCountryGrouped();
        while (groupCursor.moveToNext()){
            String iso_country = groupCursor.getString(groupCursor.getColumnIndex("iso_country"));
            listDataGroup.add(iso_country);
            List<Airport> airports = new ArrayList<>();
            Cursor airportCursor = adb.getAirportsByIso(iso_country);
            while (airportCursor.moveToNext()){
                Airport airport = new Airport();
                airport.setIcao(airportCursor.getString(airportCursor.getColumnIndex("icao")));
                airport.setName(airportCursor.getString(airportCursor.getColumnIndex("name")));
                airports.add(airport);
            }
            listDataChild.put(iso_country, airports);
        }
    }
}
