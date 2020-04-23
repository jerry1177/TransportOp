package com.example.transportop;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindRouteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindRouteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindRouteFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FindRouteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindRouteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindRouteFragment newInstance(String param1, String param2) {
        FindRouteFragment fragment = new FindRouteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    EditText fromStreet;
    EditText fromCity;
    EditText fromState;
    EditText toStreet;
    EditText toCity;
    EditText toState;
    EditText fuel;
    Spinner spinner;
    Button findRoute;
    String price;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_route, container, false);
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
        // get references to views
        fromStreet = (EditText) view.findViewById(R.id.fromStreetEditText);
        fromCity   = (EditText) view.findViewById(R.id.fromCityEditText);
        fromState  = (EditText) view.findViewById(R.id.fromStateEditText);
        toStreet   = (EditText) view.findViewById(R.id.toStreetEditText);
        toCity     = (EditText) view.findViewById(R.id.toCityEditText);
        toState    = (EditText) view.findViewById(R.id.toStateEditText);
        fuel       = (EditText) view.findViewById(R.id.currentFuelEditText);
        spinner    = (Spinner)  view.findViewById(R.id.spinner);
        findRoute  = (Button)   view.findViewById(R.id.findRouteButton);

        // set adapter for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.prices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // set on click listener for button

        findRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check to see if text boxes are empty
                if (isEmpty(fromStreet) || isEmpty(fromCity) || isEmpty(fromState) || isEmpty(fuel)
                   || isEmpty(toStreet) || isEmpty(toCity) || isEmpty(toState)) {
                    Toast.makeText(getContext(), "Make sure to fill out all of the text boxes", Toast.LENGTH_SHORT).show();
                } else if (getTextLength(fromState) != 2 || getTextLength(toState) != 2){
                    Toast.makeText(getContext(), "Make sure to use abbreviation for the state text box", Toast.LENGTH_SHORT).show();
                } else {

                    // get url
                    String url = "http://" + BuildConfig.Backend + "/api/route/find_route.php";

                    // create json object
                    String fromAddress = getString(fromStreet) + ", " + getString(fromCity) + ", " + getString(fromState);
                    String toAddress = getString(toStreet) + ", " + getString(toCity) + ", " + getString(toState);
                    VehicleModel vehicle = DriverSingleton.GetSignleton().m_Driver.m_VehiclList.get(0);

                    JSONObject params = new JSONObject();
                    try {
                        params.put("tankSize", vehicle.GetTankSize());
                        params.put("fuel", getString(fuel));
                        params.put("mpg", vehicle.GetMilesPerGalon());
                        params.put("priceTier", price);
                        params.put("toAddress", toAddress);
                        params.put("fromAddress",fromAddress);
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }


                    DriverSingleton.GetSignleton().route.clear();
                    sendRequest(params, url);

                }
            }
        });
    }

    private String getString(EditText editText) {
        return editText.getText().toString();
    }
    private int getTextLength(EditText editText) {
        return editText.getText().toString().length();
    }
    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    private void sendRequest(JSONObject params, String url) {
        // Make JSON request object
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // if valid credentials
                            if(response.getString("message").equals("success")) {
                                // reference route
                                ArrayList<ResultModel> route = DriverSingleton.GetSignleton().route;
                                // extract array
                                JSONArray jsonArray = response.getJSONArray("array");
                                JSONObject object;
                                // extract stations from array
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    object = jsonArray.getJSONObject(i);
                                    //stationModel.SetCompanyName(VendorSingleton.GetSingleton().m_Vendor.GetCompanyName());
                                    Log.v("StationList", object.toString()+"\n");

                                    ResultModel resultModel = new ResultModel(object.getString("address"),
                                            object.getString("price"), object.getString("amount"));
                                    route.add(resultModel);
                                }


                                Navigation.findNavController(getView()).navigate(FindRouteFragmentDirections.actionFindRouteFragmentToRouteResultsFragment());

                            } else {
                                Toast.makeText(getContext(), "could not obtain list of stations", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(getContext(),error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        SingletonRequestQueue.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
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
    public void onResume() {
        super.onResume();
        MainActivity main = (MainActivity)getActivity();
        if (!MainActivity.isNavBarShown) {
            main.showNavigationBar();
            MainActivity.isNavBarShown = true;
        }
        main.getSupportActionBar().setTitle("Find Route");
        main.hideUpButton();
        main.showNavigationBar();

        MainActivity.currentView = CurrentView.FINDROUTE;
        MainActivity.toView = ToView.HOME;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        price = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
