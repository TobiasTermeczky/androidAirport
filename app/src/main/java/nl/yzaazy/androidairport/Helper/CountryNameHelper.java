package nl.yzaazy.androidairport.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CountryNameHelper {

    public void getCountryNameByIso(Context context, final String iso_country, final TextView lblListGroup) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://restcountries.eu/rest/v2/alpha/" + iso_country;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            lblListGroup.setText(new JSONObject(response).getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Connection Error", "No internet connection");
            }
        });
        queue.add(stringRequest);
    }
}
