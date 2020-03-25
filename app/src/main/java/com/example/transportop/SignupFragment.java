package com.example.transportop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    RequestQueue queue;

    CircleImageView profilePic;
    ImageButton takePicture;
    EditText username;
    EditText fName;
    EditText lName;
    EditText email;
    EditText password;
    EditText cPassword;
    EditText company;
    ImageButton vendor;
    ImageButton driver;
    Button signup;

    boolean isVendor;
    boolean isDriver;

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
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profilePic = (CircleImageView) getView().findViewById(R.id.profile_image);
        username   = (EditText) getView().findViewById(R.id.Username);
        fName      = (EditText) getView().findViewById(R.id.FNTextBox);
        lName      = (EditText) getView().findViewById(R.id.LNTextBox);
        email      = (EditText) getView().findViewById(R.id.EmailTextBox);
        password   = (EditText) getView().findViewById(R.id.PasswordTextBox);
        cPassword  = (EditText) getView().findViewById(R.id.CPWTextBox);
        vendor     = (ImageButton) getView().findViewById(R.id.VendorButton);
        driver     = (ImageButton) getView().findViewById(R.id.DriverButton);
        company    = (EditText) getView().findViewById(R.id.CompanyTextBox);
        signup     = (Button) getView().findViewById(R.id.SignUpButton);
        takePicture     = (ImageButton) getView().findViewById(R.id.CameraButton);

        isVendor = false;
        isDriver = false;

        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVendor = true;
                isDriver = false;
                vendor.setBackground(getResources().getDrawable(R.drawable.rounded_corners_highlighted));
                driver.setBackground(getResources().getDrawable(R.drawable.round_corners_list_item));
            }
        });

        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVendor = false;
                isDriver = true;
                vendor.setBackground(getResources().getDrawable(R.drawable.round_corners_list_item));
                driver.setBackground(getResources().getDrawable(R.drawable.rounded_corners_highlighted));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).dispatchTakePictureIntent();
            }
        });
    }



    public void sendRequest() {
        final TextView textView = (TextView) getView().findViewById(R.id.HttpView);


        String url = "http://" + BuildConfig.Backend + "/api/user/user.php";

        // check to see if all of the text boxes are filled

        if (fName.getText().toString().isEmpty() || lName.getText().toString().isEmpty() ||
                email.getText().toString().isEmpty() || password.getText().toString().isEmpty() ) {
            Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "Make sure all text boxes are filled out!", Toast.LENGTH_SHORT).show();

        } else if (!isEmailValid(email.getText().toString())) {
            Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "Invalid Email!", Toast.LENGTH_SHORT).show();
            // check to see if passwords match
        } else if ( !password.getText().toString().equals(cPassword.getText().toString())) {
            Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "Passwords don't match!", Toast.LENGTH_SHORT).show();

            // check to see if user chose a type
        } else if (!isVendor && !isDriver) {
            Toast.makeText(((MainActivity)getActivity()).getApplicationContext(),"Must choose between driver or vendor", Toast.LENGTH_SHORT).show();

        }  else {
            Toast.makeText(((MainActivity)getActivity()).getApplicationContext(),"completed error checks", Toast.LENGTH_SHORT).show();


            /*
            // check to see if username is valid
            JSONObject params = new JSONObject();
            try {
                params.put("username", username.getText());

            } catch (JSONException e) {
                Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
            */




            JSONObject params = new JSONObject();
            try {
                params.put("username", username.getText());
                params.put("firstname", fName.getText());
                params.put("lastname", lName.getText());
                params.put("email", email.getText());
                params.put("password", password.getText());
                params.put("company", company.getText());
                if (isVendor)
                    params.put("type", "vendor");
                else
                    params.put("type", "driver");


            } catch (JSONException e) {
                Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }

            /*
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    textView.setText(response.toString());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    textView.setText(error.toString());
                }
            });
            */



            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                        try {
                            // if username is valid
                            if (response.getString("message").equals("success")) {

                                JSONObject params = new JSONObject();
                                try {
                                    params.put("username", username.getText());
                                    params.put("firstname", fName.getText());
                                    params.put("lastname", lName.getText());
                                    params.put("email", email.getText());
                                    params.put("password", password.getText());
                                    if (isVendor)
                                        params.put("type", "vendor");
                                    else
                                        params.put("type", "driver");


                                } catch (JSONException e) {
                                    Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                               // Toast.makeText(this,"completed error checks", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            textView.setText(e.toString());
                        }
                            textView.setText(response.toString());

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            textView.setText(error.toString());



                        }
                    });






            queue.add(jsonObjectRequest);
        }
    }

    /**
     * This function checks to make sure that email
     * has a valid character sequence
     * @param email
     * @return true if the email has valid email character sequence
     */
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
        main.showUpButton();
        main.hideNavigationBar();

        main.getSupportActionBar().setTitle("Sign Up");

        // update image
        if (main.imageUpdated) {
            profilePic.setImageBitmap(main.imageBitmap);
            main.imageUpdated = false;
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
