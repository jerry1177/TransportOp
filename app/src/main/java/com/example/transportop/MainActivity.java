package com.example.transportop;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,
        SignupFragment.OnFragmentInteractionListener {

    BottomNavigationView bottomNavigation;

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.profileItem:
                            Toast.makeText(getApplicationContext(), item.getItemId(), Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.settingsItem:
                            Toast.makeText(getApplicationContext(), item.getItemId(), Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.searchItem:
                            Toast.makeText(getApplicationContext(), item.getItemId(), Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.home:
                            onBackPressed();
                            return true;
                    }
                    return false;
                }

            };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
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




    public void showUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void hideUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
