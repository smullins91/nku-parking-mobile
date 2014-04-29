package com.capstoneproject.app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterFragment extends Fragment {


    private String mEmail;
    private String mPassword;
    private String mConfirmPassword;
    private String mFname;
    private String mLname;

    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private EditText mFnameView;
    private EditText mLnameView;
    private Spinner  mUserTypeView;
    private TextView  mUserTypeDisplay;
    private int mRole = -1;
    private boolean mUserSelected = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        mUserTypeView = (Spinner) v.findViewById(R.id.user_type);
        mUserTypeDisplay = (TextView) v.findViewById(R.id.user_type_display);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.usertypes_array, android.R.layout.simple_spinner_item);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mUserTypeView.setAdapter(adapter);

        mEmailView = (EditText) v.findViewById(R.id.register_email);
        mPasswordView = (EditText) v.findViewById(R.id.register_password);
        mConfirmPasswordView = (EditText) v.findViewById(R.id.register_confirm_password);
        mFnameView = (EditText) v.findViewById(R.id.register_first_name);
        mLnameView = (EditText) v.findViewById(R.id.register_last_name);

        v.findViewById(R.id.register_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mEmail = mEmailView.getText().toString().trim();
                mPassword = mPasswordView.getText().toString();
                mConfirmPassword = mConfirmPasswordView.getText().toString();
                mFname = mFnameView.getText().toString().trim();
                mLname = mLnameView.getText().toString().trim();

                boolean cancel = false;
                View focusView = null;

                if (TextUtils.isEmpty(mFname)) {
                    mFnameView.setError(getString(R.string.error_field_required));
                    focusView = mFnameView;
                    cancel = true;
                } else if (TextUtils.isEmpty(mLname)) {
                    mLnameView.setError(getString(R.string.error_field_required));
                    focusView = mLnameView;
                    cancel = true;
                } else if (TextUtils.isEmpty(mEmail)){
                    mEmailView.setError(getString(R.string.error_field_required));
                    focusView = mEmailView;
                    cancel = true;
                } else if (TextUtils.isEmpty(mPassword)){
                    mPasswordView.setError(getString(R.string.error_field_required));
                    focusView = mPasswordView;
                    cancel = true;
                } else if (TextUtils.isEmpty(mConfirmPassword)){
                    mConfirmPasswordView.setError(getString(R.string.error_field_required));
                    focusView = mConfirmPasswordView;
                    cancel = true;
                } else if (!(mPassword.equals(mConfirmPassword))){
                    mPasswordView.setError("Password fields must match!");
                    focusView = mConfirmPasswordView;
                    cancel = true;
                } else if (mPassword.length() < 4) {
                    mPasswordView.setError(getString(R.string.error_invalid_password));
                    focusView = mPasswordView;
                    cancel = true;
                }

                if (cancel) {
                    // There was an error; don't attempt register and focus on the first
                    // form field with an error.
                    focusView.requestFocus();
                } else {

                    register();
                    //Toast.makeText(getActivity(), "Registration Successful.", Toast.LENGTH_LONG).show();

                }



            }
        });


        mUserTypeDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mRole == -1) {
                    mRole = 0;
                    mUserTypeDisplay.setText((String) mUserTypeView.getSelectedItem());
                    mUserTypeDisplay.setTextColor(Color.parseColor("#555555"));
                }

                mUserSelected = true;
                mUserTypeView.performClick();

            }
        });

        mUserTypeView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(mUserSelected) {
                    mUserTypeDisplay.setText((String) mUserTypeView.getSelectedItem());
                    mUserTypeDisplay.setTextColor(Color.parseColor("#555555"));
                    mRole = i;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;

    }

    private void register() {

        final ProgressDialog progress = ProgressDialog.show(getActivity(), "", "Registering...", true);
        progress.show();

        switch(mRole) {
            case 0: mRole = 4; break;
            case 1: mRole = 2; break;
            case 2: mRole = 3; break;
            case 4: mRole = 5; break;
        }

        NetworkHelper.register(getActivity(), mEmail, mFname, mLname, mPassword, mRole, new HttpResponse(getActivity()) {

            @Override
            public void onFailure(Throwable e, JSONObject result) {

                try {
                    progress.dismiss();
                    String error = result.getString("error");
                    AlertDialog.Builder b = new AlertDialog.Builder(RegisterFragment.this.getActivity());
                    b.setTitle("Error");
                    b.setMessage(error);
                    b.setNeutralButton("OK", null);
                    b.show();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void onSuccess(JSONObject result) {
                try {
                    String key = result.getString("key");
                    int type;
                    //Set the account type to match the lot types
                    switch (mRole) {
                        case 2:
                        case 3:
                            type = 1; break; //Faculty/Staff
                        case 4:
                            type = 2; break; //Student
                        case 5:
                            type = 3; break;
                        default: type = 3;
                    }

                    SettingsHelper settings = new SettingsHelper(getActivity());
                    settings.setSessionKey(key);
                    settings.setType(type);


                    progress.dismiss();

                    Intent i = new Intent(getActivity(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                    getActivity().finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });


    }


}
