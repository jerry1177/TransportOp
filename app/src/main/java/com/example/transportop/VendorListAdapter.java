package com.example.transportop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class VendorListAdapter extends ArrayAdapter<StationModel> {


    Context context;
    int resource;
    ArrayList<StationModel> list;

    @Override
    public int getCount() {
        return list.size();
    }

    public VendorListAdapter(Context context, int resource, ArrayList<StationModel> stations) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.list = stations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // get the view
        View view = layoutInflater.inflate(resource, null, false);

        TextView streetAddress = view.findViewById(R.id.streetAddress);
        TextView regPrice = view.findViewById(R.id.regPrice);
        TextView midPrice = view.findViewById(R.id.midPrice);
        TextView premPrice = view.findViewById(R.id.premPrice);
        TextView dieselPrice = view.findViewById(R.id.dieselPrice);

        Toast.makeText(context, "view created", Toast.LENGTH_SHORT).show();

        StationModel stationModel = list.get(position);

        streetAddress.setText(stationModel.GetAddress());
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        regPrice.setText(decimalFormat.format(stationModel.GetRegPrice()));

        midPrice.setText(decimalFormat.format(stationModel.GetMidPrice()));
        premPrice.setText(decimalFormat.format(stationModel.GetPremPrice()));
        dieselPrice.setText(decimalFormat.format(stationModel.GetDieselPrice()));




        return view;
    }

}
