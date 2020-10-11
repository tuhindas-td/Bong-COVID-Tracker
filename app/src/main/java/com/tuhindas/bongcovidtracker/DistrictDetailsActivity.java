package com.tuhindas.bongcovidtracker;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Objects;

public class DistrictDetailsActivity extends AppCompatActivity {

    TextView districtActive, districtConfirmed, districtRecovered, districtDeceased;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_details);
        Intent intent = getIntent();
        int positionDistrict = intent.getIntExtra("position", 0);
        Objects.requireNonNull(getSupportActionBar()).setTitle(DistrictData.districtModelsList.get(positionDistrict).getDistrict() + " Cases");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        districtActive = findViewById(R.id.districtActive);
        districtConfirmed = findViewById(R.id.districtConfirmed);
        districtRecovered = findViewById(R.id.districtRecovered);
        districtDeceased = findViewById(R.id.districtDeceased);

        districtActive.setText(DistrictData.districtModelsList.get(positionDistrict).getActive());
        districtConfirmed.setText(DistrictData.districtModelsList.get(positionDistrict).getConfirmed());
        districtRecovered.setText(DistrictData.districtModelsList.get(positionDistrict).getRecovered());
        districtDeceased.setText(DistrictData.districtModelsList.get(positionDistrict).getDeceased());
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}