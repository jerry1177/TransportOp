package com.example.transportop;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment<OnPause> extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ArrayList<VehicleModel> vehicleList;
    ArrayList<StationModel> stationList;
    ListView listView;
    ViewManagerSingleton viewManager;

    DriverListAdapter dAdapter;
    VendorListAdapter vAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        viewManager = ViewManagerSingleton.GetSingleton();
        // Get user list

        //*
        // Make post url

        JSONObject params = new JSONObject();
        JsonObjectRequest jsonObjectRequest;


        if (viewManager.getUserType() == UserType.DRIVER) {
            String url = "http://" + BuildConfig.Backend + "/api/vehicle/read_all.php";
            vehicleList = DriverSingleton.GetSignleton().m_Driver.m_VehiclList;
            // Make post JSON
            try {
                params.put("username", DriverSingleton.GetSignleton().m_Driver.GetUserName());
            } catch (JSONException e) {
                //Toast.makeText(getContext(), "JSON OBJECT ERROR", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }

            // Make JSON request object
            jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // if valid credentials
                                if(response.getString("message").equals("success")) {
                                    // extract array
                                    JSONArray jsonArray = response.getJSONArray("array");
                                    JSONObject object;
                                    // extract vehicles from array
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        VehicleModel vehicleModel = new VehicleModel();
                                        object = jsonArray.getJSONObject(i);
                                        vehicleModel.SetVehicleModel(object.getString("name"));
                                        vehicleModel.SetMilesPerGalon(((float)object.getDouble("mpg")));
                                        if (object.getString("dieselOnly").equals("1"))
                                            vehicleModel.SetIsDieselOnly(true);
                                        else
                                            vehicleModel.SetIsDieselOnly(false);

                                        vehicleModel.SetTankSize(object.getInt("tankSize"));
                                        vehicleList.add(vehicleModel);
                                    }
                                    dAdapter.notifyDataSetChanged();

                                } else {
                                    Toast.makeText(getContext(), "could not obtain list", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getContext(), "retrieve JSON OBJECT ERROR", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Toast.makeText(getContext(),error.toString(), Toast.LENGTH_SHORT).show();

                            //Toast.makeText(getContext(), "JSON Request ERROR", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
        //*
        else {
            String url = "http://" + BuildConfig.Backend + "/api/station/read_all.php";
            stationList = VendorSingleton.GetSingleton().m_Vendor.m_StationList;


            // Make post JSON
            try {
                params.put("username", VendorSingleton.GetSingleton().m_Vendor.GetUserName());

            } catch (JSONException e) {
                //Toast.makeText(getContext(), "JSON OBJECT ERROR", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }

            // Make JSON request object
            jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // if valid credentials
                                if(response.getString("message").equals("success")) {
                                    // extract array
                                    JSONArray jsonArray = response.getJSONArray("array");
                                    JSONObject object;
                                    // extract stations from array
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        StationModel stationModel = new StationModel();
                                        object = jsonArray.getJSONObject(i);
                                        //stationModel.SetCompanyName(VendorSingleton.GetSingleton().m_Vendor.GetCompanyName());

                                        stationModel.SetAddress(object.getString("address"));
                                        stationModel.SetRegPrice(Float.parseFloat(object.getString("reg")));
                                        stationModel.SetMidPrice(Float.parseFloat(object.getString("med")));
                                        stationModel.SetPremPrice(Float.parseFloat(object.getString("prem")));
                                        stationModel.SetDieselPrice(Float.parseFloat(object.getString("diesel")));
                                        stationList.add(stationModel);
                                    }
                                    vAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getContext(), "could not obtain list", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getContext(), "retrieve JSON OBJECT ERROR", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Toast.makeText(getContext(),error.toString(), Toast.LENGTH_SHORT).show();

                            //Toast.makeText(getContext(), "JSON Request ERROR", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

          //   */

        SingletonRequestQueue.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);


        /*
        if (viewManager.getUserType() == UserType.DRIVER)
            vehicleList = DriverSingleton.GetSignleton().m_Driver.m_VehiclList;
        else
            stationList = VendorSingleton.GetSingleton().m_Vendor.m_StationList;
         */
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) getView().findViewById(R.id.homeListView);



        if (viewManager.getUserType() == UserType.DRIVER) {
            dAdapter = new DriverListAdapter(getContext(), R.layout.driver_list_view, vehicleList);
            listView.setAdapter(dAdapter);
            //Toast.makeText(getContext(), "stationlistview entered", Toast.LENGTH_SHORT).show();
        }
        else {
            vAdapter = new VendorListAdapter(getContext(), R.layout.station_list_view, stationList);
            listView.setAdapter(vAdapter);
        }


         //*/



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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




        main.getSupportActionBar().setTitle("Dashboard");
        MainActivity.currentView = CurrentView.HOME;

        if (MainActivity.toView == ToView.FINDROUTE)
            Navigation.findNavController(getView()).navigate(HomeFragmentDirections.actionHomeFragmentToFindRouteFragment());

        if (MainActivity.toView == ToView.ADDVEHICLE)
            Navigation.findNavController(getView()).navigate(HomeFragmentDirections.actionHomeFragmentToAddVehicleFragment());

        MainActivity.toView = ToView.LOGIN;
    }


    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getActivity().getApplicationContext(), "paused", Toast.LENGTH_SHORT).show();
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
