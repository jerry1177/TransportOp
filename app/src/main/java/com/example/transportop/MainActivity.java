package com.example.transportop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,
        SignupFragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener,
        FindRouteFragment.OnFragmentInteractionListener, AddFragment.OnFragmentInteractionListener,
        CreateVehicleFragment.OnFragmentInteractionListener, CreateStationFragment.OnFragmentInteractionListener,
        RouteResultFragment.OnFragmentInteractionListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    static CurrentView currentView;
    static ToView toView = ToView.LOGIN;
    static boolean imageUpdated = false;
    static Bitmap imageBitmap;
    static boolean isNavBarShown = false;

    BottomNavigationView bottomNavigation;

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    View view = getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment).getView();

                    if (item.getItemId() == R.id.homeItem) {
                        if (currentView != CurrentView.HOME) {
                            Navigation.findNavController(view).popBackStack();
                            toView = ToView.HOME;
                            //Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    } else if (item.getItemId() == R.id.middleItem) {
                        if (currentView == CurrentView.HOME) {
                            Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToFindRouteFragment());
                            //Toast.makeText(getApplicationContext(), "find", Toast.LENGTH_SHORT).show();
                        }

                        if (currentView == CurrentView.ADDVEHICLE) {
                            Navigation.findNavController(view).popBackStack();
                            toView = ToView.FINDROUTE;
                        }

                        return true;
                    } else if ( item.getItemId() == R.id.addItem) {
                        if (currentView == CurrentView.HOME) {
                            Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeFragmentToAddVehicleFragment());

                        }

                        if (currentView == CurrentView.FINDROUTE) {
                            Navigation.findNavController(view).popBackStack();
                            toView = ToView.ADDVEHICLE;
                        }
                        return true;
                    } else {
                        onBackPressed();
                        return true;
                    }
                }

            };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        ViewManagerSingleton.GetSingleton().setUserType(UserType.DRIVER);

        DriverSingleton.GetSignleton().m_Driver.SetCompanyName("Beasty");
        DriverSingleton.GetSignleton().m_Driver.m_VehiclList.add(new VehicleModel("Honda Civic", 29.0f, 12, false));
        DriverSingleton.GetSignleton().m_Driver.m_VehiclList.add(new VehicleModel("toyota", 25.0f, 15, false));
        DriverSingleton.GetSignleton().m_Driver.m_VehiclList.add(new VehicleModel("camaro", 15.0f, 25, false));

        VendorSingleton.GetSingleton().m_Vendor.m_StationList.add(new StationModel("La Mirada", 3.30f,3.34f,3.35f,2.78f));
        VendorSingleton.GetSingleton().m_Vendor.m_StationList.add(new StationModel("Seaside", 3.31f,3.36f,3.37f,3.78f));
        VendorSingleton.GetSingleton().m_Vendor.m_StationList.add(new StationModel("Monterey", 1.30f,2.34f,5.35f,4.78f));



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageUpdated = true;

        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //Toast.makeText(getApplicationContext(), item.getItemId(), Toast.LENGTH_SHORT).show();
                onBackPressed();
                // Do something here. This is the event fired when up button is pressed.
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void showNavigationBar() {
        bottomNavigation.setVisibility(View.VISIBLE);
        isNavBarShown = true;
    }
    public void hideNavigationBar() {

        bottomNavigation.setVisibility(View.GONE);
        isNavBarShown = false;
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void showUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void hideUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
