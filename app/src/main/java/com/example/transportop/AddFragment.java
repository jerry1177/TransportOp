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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ListView listView;
    FloatingActionButton fab;
    ArrayList<VehicleModel> vehicleList;
    ArrayList<StationModel> stationList;
    ViewManagerSingleton viewManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        viewManager = ViewManagerSingleton.GetSingleton();

        if (viewManager.getUserType() == UserType.DRIVER)
            vehicleList = DriverSingleton.GetSignleton().m_Driver.m_VehiclList;
        else
            stationList = VendorSingleton.GetSingleton().m_Vendor.m_StationList;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_vehicle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) getView().findViewById(R.id.addVehicleListView);

        DriverListAdapter dAdapter;// = new DriverListAdapter(getContext(), R.layout.driver_list_view, vehicleList);
        VendorListAdapter vAdapter;

        if (viewManager.getUserType() == UserType.DRIVER) {
            dAdapter = new DriverListAdapter(getContext(), R.layout.driver_list_view, vehicleList);
            listView.setAdapter(dAdapter);
        }
        else {
            vAdapter = new VendorListAdapter(getContext(), R.layout.station_list_view, stationList);
            listView.setAdapter(vAdapter);
        }

        //listView.setAdapter(dAdapter);

        fab = (FloatingActionButton) getView().findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewManager.getUserType() == UserType.DRIVER)
                    Navigation.findNavController(getView()).navigate(AddFragmentDirections.actionAddVehicleFragmentToCreateVehicleFragment());
                else
                    Navigation.findNavController(getView()).navigate(AddFragmentDirections.actionAddVehicleFragmentToCreateStationFragment());
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
    public void onResume() {
        super.onResume();
        MainActivity main = (MainActivity)getActivity();
        if (!MainActivity.isNavBarShown) {
            main.showNavigationBar();
            MainActivity.isNavBarShown = true;
        }

        main.getSupportActionBar().setTitle("Add Vehicle");
        main.hideUpButton();

        MainActivity.currentView = CurrentView.ADDVEHICLE;
        MainActivity.toView = ToView.HOME;
        //Toast.makeText(getActivity().getApplicationContext(), main.currentView.toString(), Toast.LENGTH_SHORT).show();
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
