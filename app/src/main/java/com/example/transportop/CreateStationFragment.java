package com.example.transportop;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateStationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateStationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateStationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateStationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateStationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateStationFragment newInstance(String param1, String param2) {
        CreateStationFragment fragment = new CreateStationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    EditText address;
    EditText regPrice;
    EditText midPrice;
    EditText premPrice;
    EditText dieselPrice;
    Button createStation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        MainActivity main = (MainActivity) getActivity();
        main.showUpButton();
        main.hideNavigationBar();
        main.getSupportActionBar().setTitle("Create Station");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_station, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        address = (EditText) view.findViewById(R.id.createStationAddressEditText);
        regPrice = (EditText) view.findViewById(R.id.createStationRegPriceEditText);
        midPrice = (EditText) view.findViewById(R.id.createStationMidPriceEditText);
        premPrice = (EditText) view.findViewById(R.id.createStationPremPriceEditText);
        dieselPrice = (EditText) view.findViewById(R.id.createStationDieselPriceEditText);
        createStation = (Button) view.findViewById(R.id.createStationButton);

        createStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://" + BuildConfig.Backend + "";
                StationModel stationModel = new StationModel();
                stationModel.SetAddress(address.getText().toString());
                stationModel.SetRegPrice(Float.parseFloat(regPrice.getText().toString()));
                stationModel.SetMidPrice(Float.parseFloat(midPrice.getText().toString()));
                stationModel.SetPremPrice(Float.parseFloat(premPrice.getText().toString()));
                stationModel.SetDieselPrice(Float.parseFloat(dieselPrice.getText().toString()));
                VendorSingleton.GetSingleton().m_Vendor.m_StationList.add(stationModel);
                //sendRequest(stationModel, getContext(), url);

                getActivity().onBackPressed();
            }
        });
    }

    private void sendRequest(final StationModel stationModel, final Context context, String url) {


        JSONObject params = new JSONObject();
        try {
            params.put("address", stationModel.GetAddress());
            params.put("regPrice", stationModel.GetRegPrice());
            params.put("midPrice", stationModel.GetMidPrice());
            params.put("premPrice", stationModel.GetPremPrice());
            params.put("dieselPrice", stationModel.GetDieselPrice());
        } catch (JSONException e){
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("message").equals("success")) {
                                Toast.makeText(getContext(), "successfully created vehicle!", Toast.LENGTH_SHORT).show();
                            } else {
                                // if the request failed, we need to delete the last item on
                                // the list that we added before we sent the request
                                
                                ArrayList<StationModel> list = VendorSingleton.GetSingleton().m_Vendor.m_StationList;
                                list.remove(list.size()-1);
                                Toast.makeText(getContext(), "Sorry, couldn't make station", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getContext(), e.toString(),Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(getContext(),error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
