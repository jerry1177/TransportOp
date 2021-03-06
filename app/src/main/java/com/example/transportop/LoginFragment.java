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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RequestQueue queue;
    private EditText username;
    private EditText password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }

        queue = SingletonRequestQueue.getInstance(((MainActivity)getActivity()).getApplicationContext()).getRequestQueue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Link username and password to text boxes
        username = view.findViewById(R.id.loginUsernameEditText);
        password = view.findViewById(R.id.loginPasswordEditText);


        // Set the signup and login on click listeners
        view.findViewById(R.id.toSignUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(LoginFragmentDirections.actionLoginToSignupFragment());
            }
        });

        // perform action when login button is tapped
        view.findViewById(R.id.LoginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Make sure all text boxes are filled", Toast.LENGTH_SHORT).show();
                } else {
                    sendRequest();
                }
            }
        });
    }

    private void sendRequest() {
        // Make post url
        String url = "http://" + BuildConfig.Backend + "/api/user/login.php";

        // Make post JSON
        JSONObject params = new JSONObject();
        try {
            params.put("username", username.getText());
            params.put("password", password.getText());
        } catch (JSONException e) {
            Toast.makeText(getContext(), "JSON OBJECT ERROR", Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        // Make JSON request object
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // if valid credentials
                            if(response.getString("message").equals("success")) {
                                // check the user type
                                if (response.getString("type").equals("vendor")) {
                                    ViewManagerSingleton.GetSingleton().setUserType(UserType.VENDOR);
                                    Vendor vendor = new Vendor();
                                    vendor.SetUserName(username.getText().toString());
                                    vendor.SetFirstName(response.getString("fname"));
                                    vendor.SetLastName(response.getString("lname"));
                                    vendor.SetEmail(response.getString("email"));
                                    vendor.SetPassword(password.getText().toString());
                                    vendor.SetCompanyName(response.getString("company"));
                                    VendorSingleton.GetSingleton().m_Vendor = vendor;
                                } else {
                                    ViewManagerSingleton.GetSingleton().setUserType(UserType.DRIVER);
                                    Driver driver = new Driver();
                                    driver.SetUserName(username.getText().toString());
                                    driver.SetFirstName(response.getString("fname"));
                                    driver.SetLastName(response.getString("lname"));
                                    driver.SetEmail(response.getString("email"));
                                    driver.SetPassword(password.getText().toString());
                                    driver.SetCompanyName(response.getString("company"));
                                    DriverSingleton.GetSignleton().m_Driver = driver;
                                }
                                Navigation.findNavController(getView()).navigate(LoginFragmentDirections.actionLoginToHomeFragment());
                            } else {
                                Toast.makeText(getContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
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
        // Send request
        queue.add(jsonObjectRequest);
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

    @Override
    public void onResume() {
        super.onResume();

        if (MainActivity.toView == ToView.HOME)
            Navigation.findNavController(getView()).navigate(LoginFragmentDirections.actionLoginToHomeFragment());

        MainActivity main = (MainActivity) getActivity();
        main.hideNavigationBar();
        main.hideUpButton();
        // change the page title to login
        main.getSupportActionBar().setTitle("login");

    }

    @Override
    public void onStop() {
        super.onStop();
        username.setText("");
        password.setText("");
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
