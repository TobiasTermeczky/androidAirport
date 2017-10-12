package nl.yzaazy.androidairport;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import nl.yzaazy.androidairport.Fragment.AirportDetailFragment;
import nl.yzaazy.androidairport.Helper.AirportsDatabase;
import nl.yzaazy.androidairport.Helper.Haversine;
import nl.yzaazy.androidairport.Model.Airport;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

public class AirportActivity extends FragmentActivity implements OnMapReadyCallback {

    AirportsDatabase adb;
    private GoogleMap map;
    private Airport airport;
    private Airport schiphol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_airport);

        adb = new AirportsDatabase(getApplicationContext());
        Bundle extras = getIntent().getExtras();

        airport = adb.getAirportByIcao(extras.getString("icao"));
        schiphol = adb.getAirportByIcao("EHAM");

        AirportDetailFragment detailFragment = (AirportDetailFragment) getSupportFragmentManager().findFragmentById(R.id.airport_fragment);
        detailFragment.populateDetails(airport, calculateKM(airport, schiphol));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private double calculateKM(Airport airport, Airport schiphol) {
        double R = 6371;
        double phi1 = toRadians(airport.getLatitude());
        double phi2 = toRadians(schiphol.getLatitude());
        double delta_phi = toRadians(schiphol.getLatitude() - airport.getLatitude());
        double delta_lambda = toRadians(schiphol.getLongitude() - airport.getLongitude());

        double a = sin(delta_phi / 2) * sin(delta_phi / 2) + cos(phi1) * cos(phi2) * sin(delta_lambda / 2) * sin(delta_lambda / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1-a));
        double d = R * c;
        return d;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng airportLatLng = new LatLng(airport.getLatitude(), airport.getLongitude());
        LatLng schipholLatLng = new LatLng(schiphol.getLatitude(), schiphol.getLongitude());
        // Add a marker in the airport and move the camera

        map.addMarker(new MarkerOptions()
                .position(airportLatLng)
                .title(airport.getName())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        map.addMarker(new MarkerOptions()
                .position(schipholLatLng)
                .title(schiphol.getName())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        map.addPolyline(new PolylineOptions()
                .add(airportLatLng, schipholLatLng)
                .width(5)
                .color(Color.BLUE));

        map.addPolyline(new PolylineOptions()
                .add(airportLatLng, schipholLatLng)
                .width(5)
                .color(Color.RED)).setGeodesic(true);

        map.moveCamera(CameraUpdateFactory.newLatLng(airportLatLng));
    }
}
