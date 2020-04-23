package com.example.transportop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ResultListAdapter extends ArrayAdapter<ResultModel> {

    ArrayList<ResultModel> list;
    Context context;
    int resource;
    public ResultListAdapter(@NonNull Context context, int resource, ArrayList<ResultModel> list) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // get the view
        View view = layoutInflater.inflate(resource, null, false);

        // get the text views
        TextView resultAddress = (TextView) view.findViewById(R.id.resultAddress);
        TextView resultPrice   = (TextView) view.findViewById(R.id.resultPrice);
        TextView resultAmount  = (TextView) view.findViewById(R.id.resultAmount);

        // get result from list
        ResultModel result = list.get(position);

        // set result texts in view
        resultAddress.setText(result.getStreetAddres());
        resultPrice.setText(result.getPrice());
        resultAmount.setText(result.getAmount());


        return view;
    }
}
