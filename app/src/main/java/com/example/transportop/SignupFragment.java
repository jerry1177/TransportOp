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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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


    private RequestQueue queue;

    private CircleImageView profilePic;
    ImageButton takePicture;
    private EditText username;
    private EditText fName;
    private EditText lName;
    private EditText email;
    private EditText password;
    private EditText cPassword;
    private EditText company;
    private ImageButton vendor;
    private ImageButton driver;
    Button signup;

    private boolean isVendor;
    private boolean isDriver;

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
        username   = (EditText) view.findViewById(R.id.Username);
        fName      = (EditText) view.findViewById(R.id.FNTextBox);
        lName      = (EditText) view.findViewById(R.id.LNTextBox);
        email      = (EditText) view.findViewById(R.id.EmailTextBox);
        password   = (EditText) view.findViewById(R.id.PasswordTextBox);
        cPassword  = (EditText) view.findViewById(R.id.CPWTextBox);
        vendor     = (ImageButton) view.findViewById(R.id.VendorButton);
        driver     = (ImageButton) view.findViewById(R.id.DriverButton);
        company    = (EditText) view.findViewById(R.id.CompanyTextBox);
        signup     = (Button) view.findViewById(R.id.SignUpButton);
        takePicture     = (ImageButton) view.findViewById(R.id.CameraButton);

        isVendor = false;
        isDriver = false;

        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVendor = true;
                isDriver = false;
                vendor.setBackground(getResources().getDrawable(R.drawable.rounded_corners_highlighted));
                driver.setBackground(getResources().getDrawable(R.drawable.round_corners_list_item));
                company.setHint("Company (Mandatory)");
            }
        });

        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVendor = false;
                isDriver = true;
                vendor.setBackground(getResources().getDrawable(R.drawable.round_corners_list_item));
                driver.setBackground(getResources().getDrawable(R.drawable.rounded_corners_highlighted));
                company.setHint("Company (Optional)");
            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).dispatchTakePictureIntent();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendRequest();
                if (fName.getText().toString().isEmpty() || lName.getText().toString().isEmpty() ||
                        email.getText().toString().isEmpty() || password.getText().toString().isEmpty() ) {
                    Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "Make sure all text boxes are filled out!", Toast.LENGTH_SHORT).show();

                    // Check to see if the email has valid syntax
                } else if (!isEmailValid(email.getText().toString())) {
                    Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "Invalid Email!", Toast.LENGTH_SHORT).show();
                    // check to see if passwords match
                } else if ( !password.getText().toString().equals(cPassword.getText().toString())) {
                    Toast.makeText(((MainActivity)getActivity()).getApplicationContext(), "Passwords don't match!", Toast.LENGTH_SHORT).show();

                    // check to see if user chose a type
                } else if (!isVendor && !isDriver) {
                    Toast.makeText(((MainActivity)getActivity()).getApplicationContext(),"Must choose between driver or vendor", Toast.LENGTH_SHORT).show();

                    // passed all of the errors
                }  else {
                    sendRequest();
                }
            }
        });
    }


    public void sendRequest() {
        final TextView textView = (TextView) getView().findViewById(R.id.HttpView);


        String url = "http://" + BuildConfig.Backend + "/api/user/user.php";

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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("message").equals("success")) {
                                if (isVendor) {
                                    Vendor vendor = new Vendor();
                                    vendor.SetUserName(username.getText().toString());
                                    vendor.SetFirstName(fName.getText().toString());
                                    vendor.SetLastName(lName.getText().toString());
                                    vendor.SetEmail(email.getText().toString());
                                    vendor.SetPassword(password.getText().toString());
                                    vendor.SetCompanyName(company.getText().toString());

                                    VendorSingleton.GetSingleton().m_Vendor = vendor;

                                } else {
                                    // create driver
                                    Driver driver = new Driver();
                                    driver.SetUserName(username.getText().toString());
                                    driver.SetFirstName(fName.getText().toString());
                                    driver.SetLastName(lName.getText().toString());
                                    driver.SetEmail(email.getText().toString());
                                    driver.SetPassword(password.getText().toString());
                                    driver.SetCompanyName(company.getText().toString());

                                    // set singleton driver
                                    DriverSingleton.GetSignleton().m_Driver = driver;
                                }

                                MainActivity.toView = ToView.HOME;
                                Navigation.findNavController(getView()).popBackStack();
                            } else {
                                textView.setText(response.getString("message"));
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
