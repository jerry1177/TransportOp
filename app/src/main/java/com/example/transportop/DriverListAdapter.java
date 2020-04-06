package com.example.transportop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DriverListAdapter extends ArrayAdapter<VehicleModel> {

    ArrayList<VehicleModel> list;

    Context context;

    int resource;

    DriverListAdapter(Context context, int resource, ArrayList<VehicleModel> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // get the view
        View view = layoutInflater.inflate(resource, null, false);

        TextView companyName =  (TextView) view.findViewById(R.id.driverCompanyName);
        TextView modelText =    (TextView) view.findViewById(R.id.modelText);
        TextView mpgText =      (TextView) view.findViewById(R.id.mpgText);
        TextView tankSizeText = (TextView) view.findViewById(R.id.tankSizeText);
        TextView dieselText =   (TextView) view.findViewById(R.id.dieselOnlyText);

        companyName.setText(DriverSingleton.GetSignleton().m_Driver.GetCompanyName());
        VehicleModel vehicle = list.get(position);

        modelText.setText(vehicle.GetVehicleModel());
        mpgText.setText("mil/g: " + vehicle.GetMilesPerGalon());
        tankSizeText.setText("Tank Size: " + vehicle.GetTankSize());
        if (vehicle.IsDieselOnly()) {
            dieselText.setText("Diesel Only: Yes");
        } else {
            dieselText.setText("Diesel Only: No");
        }

        return view;
    }
}
