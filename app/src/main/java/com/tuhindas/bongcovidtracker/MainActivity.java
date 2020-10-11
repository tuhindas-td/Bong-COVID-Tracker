package com.tuhindas.bongcovidtracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView todayDate, confirmed, active, recovered, deceased, confirmed_24hours, recovered_24hours, deceased_24hours;
    PieChart pieChart;
    JSONArray statewiseData;
    JSONArray statesDailyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todayDate = findViewById(R.id.todayDate);
        confirmed = findViewById(R.id.confirmed);
        active = findViewById(R.id.active);
        recovered = findViewById(R.id.recovered);
        deceased = findViewById(R.id.deceased);
        confirmed_24hours = findViewById(R.id.confirmed_24hours);
        recovered_24hours = findViewById(R.id.recovered_24hours);
        deceased_24hours = findViewById(R.id.deceased_24hours);
        pieChart = findViewById(R.id.piechart);

        fetchData();
    }

    private void fetchData() {

        String stateswiseDataUrl = "https://api.covid19india.org/data.json";
        String statesDailyDataUrl = "https://api.covid19india.org/states_daily.json";
        StringRequest request1 = new StringRequest(Request.Method.GET, stateswiseDataUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    statewiseData = new JSONObject(response).getJSONArray("statewise");
                    allCases();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        StringRequest request2 = new StringRequest(Request.Method.GET, statesDailyDataUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    statesDailyData = new JSONObject(response).getJSONArray("states_daily");
                    lastDayCases();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(request1);
        MySingleton.getInstance(this).addToRequestQueue(request2);
    }

    private void lastDayCases() throws JSONException {

        for (int i = statesDailyData.length() - 1; i >= statesDailyData.length() - 3; i--) {
            JSONObject states = (JSONObject) statesDailyData.get(i);
            if (states.getString("status").equals("Confirmed")) {
                confirmed_24hours.setText(states.getString("wb"));
            }
            if ( states.getString("status").equals("Recovered")) {
                recovered_24hours.setText(states.getString("wb"));
            }
            if ( states.getString("status").equals("Deceased")) {
                deceased_24hours.setText(states.getString("wb"));
            }
        }
    }

    private void allCases() throws JSONException {
        for (int i = 0; i < statewiseData.length(); i++) {
            JSONObject stateData = (JSONObject) statewiseData.get(i);
            if (stateData.getString("statecode").equals("WB")) {
                todayDate.setText(stateData.getString("lastupdatedtime"));
                confirmed.setText(stateData.getString("confirmed"));
                active.setText(stateData.getString("active"));
                recovered.setText(stateData.getString("recovered"));
                deceased.setText(stateData.getString("deaths"));
                pieChart.addPieSlice(new PieModel("Confirmed", Integer.parseInt(confirmed.getText().toString()), Color.parseColor("#FFA726")));
                pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(active.getText().toString()), Color.parseColor("#29B6F6")));
                pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(recovered.getText().toString()), Color.parseColor("#66BB6A")));
                pieChart.addPieSlice(new PieModel("Deceased", Integer.parseInt(deceased.getText().toString()), Color.parseColor("#EF5350")));
            }
        }
    }

    public void trackDistricts(View view) {
        Intent intent = new Intent(getApplicationContext(), DistrictData.class);
        startActivity(intent);
    }
}