package com.tuhindas.bongcovidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListCustomAdapter extends ArrayAdapter<DistrictModel> {

    private final List<DistrictModel> districtModelsList;
    public ListCustomAdapter(Context context, List<DistrictModel> districtModelsList) {

        super(context, R.layout.list_custom_item, districtModelsList);
        this.districtModelsList = districtModelsList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item, null, true);
        TextView districtName = view.findViewById(R.id.districtName);
        districtName.setText(districtModelsList.get(position).getDistrict());
        return view;
    }
}
