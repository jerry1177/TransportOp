package com.example.transportop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * enum to keep track of what view is
 * curently being displayed or on top of the stack
 */
enum CurrentView {
    HOME,
    FINDROUTE,
    ADDVEHICLE
}

/**
 * enum to keep track of what view
 * the home fragment should navigate to because
 * findroute and addvehicle fragments just pop back to homeview
 */
enum ToView {
    HOME,
    FINDROUTE,
    ADDVEHICLE
}

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,
        SignupFragment.OnFragmentInteractionListener, VehicleHomeFragment.OnFragmentInteractionListener,
        FindRouteFragment.OnFragmentInteractionListener, AddVehicleFragment.OnFragmentInteractionListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    static CurrentView currentView;
    static ToView toView = ToView.HOME;
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
                            Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    } else if (item.getItemId() == R.id.middleItem) {
                        if (currentView == CurrentView.HOME) {
                            Navigation.findNavController(view).navigate(VehicleHomeFragmentDirections.actionHomeFragmentToFindRouteFragment());
                            Toast.makeText(getApplicationContext(), "find", Toast.LENGTH_SHORT).show();
                        }

                        if (currentView == CurrentView.ADDVEHICLE) {
                            Navigation.findNavController(view).popBackStack();
                            toView = ToView.FINDROUTE;
                        }

                        return true;
                    } else if ( item.getItemId() == R.id.addVehicleItem) {
                        if (currentView == CurrentView.HOME) {
                            Navigation.findNavController(view).navigate(VehicleHomeFragmentDirections.actionHomeFragmentToAddVehicleFragment());

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
        bottomNavigation.getMenu().close();

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
    }
    public void hideNavigationBar() {
        bottomNavigation.setVisibility(View.GONE);
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
