package com.tuhindas.bongcovidtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DistrictData extends AppCompatActivity {

    ListView listView;
    public static List<DistrictModel> districtModelsList = new ArrayList<>();
    DistrictModel districtModel;
    ListCustomAdapter listCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_data);
        listView = findViewById(R.id.listView);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Districts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), DistrictDetailsActivity.class).putExtra("position", position));
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {

        String districtwiseDataUrl = "https://api.covid19india.org/v2/state_district_wise.json";
        StringRequest request = new StringRequest(Request.Method.GET, districtwiseDataUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray states = new JSONArray(response);
                    JSONArray districts = states.getJSONObject(states.length() - 1).getJSONArray("districtData");
                    for (int i = 0; i < districts.length(); i++) {

                        JSONObject districtData = districts.getJSONObject(i);
                        String districtName = districtData.getString("district");
                        String active = districtData.getString("active");
                        String confirmed = districtData.getString("confirmed");
                        String recovered = districtData.getString("recovered");
                        String deceased = districtData.getString("deceased");
                        districtModel = new DistrictModel(districtName, active, confirmed, recovered, deceased);
                        districtModelsList.add(districtModel);
                    }
                    listCustomAdapter = new ListCustomAdapter(DistrictData.this, districtModelsList);
                    listView.setAdapter(listCustomAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DistrictData.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(request);
    }
}