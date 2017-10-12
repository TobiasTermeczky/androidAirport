package nl.yzaazy.androidairport.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import nl.yzaazy.androidairport.Model.Airport;
import nl.yzaazy.androidairport.R;

public class AirportDetailFragment extends android.support.v4.app.Fragment {

    TextView tvIcao;
    TextView tvName;
    TextView tvLongitude;
    TextView tvLatitude;
    TextView tvElevation;
    TextView tvISO_Country;
    TextView tvMunicipality;
    TextView tvKm;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.airport_detail_fragment, container, false);
        tvIcao = view.findViewById(R.id.tvIcao);
        tvName = view.findViewById(R.id.tvName);
        tvLongitude = view.findViewById(R.id.tvLongitude);
        tvLatitude = view.findViewById(R.id.tvLatitude);
        tvElevation = view.findViewById(R.id.tvElevation);
        tvISO_Country = view.findViewById(R.id.tvISO_Country);
        tvMunicipality = view.findViewById(R.id.tvMunicipality);
        tvKm = view.findViewById(R.id.tvKm);
        return view;
    }

    public void populateDetails(Airport airport, double km){
        tvIcao.setText(airport.getIcao());
        tvName.setText(airport.getName());
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
        tvLongitude.setText(df.format(airport.getLongitude()));
        tvLatitude.setText(df.format(airport.getLatitude()));
        tvElevation.setText(df.format(airport.getElevation()));
        tvISO_Country.setText(airport.getIso_country());
        tvMunicipality.setText(airport.getMunicipality());
        tvKm.setText(String.valueOf((int) km));
    }
}
