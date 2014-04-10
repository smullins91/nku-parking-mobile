package com.capstoneproject.app;

import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class RegisterFragment extends Fragment {


    private String mEmail;
    private String mPassword;
    private String mConfirmPassword;
    private String mFname;
    private String mLname;
    private String mUserType;

    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private EditText mFnameView;
    private EditText mLnameView;
    private Spinner  mUserTypeView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_register, container, false);




        mUserTypeView = (Spinner) v.findViewById(R.id.user_type);

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


                mEmail = mEmailView.getText().toString();
                mPassword = mPasswordView.getText().toString();
                mConfirmPassword = mConfirmPasswordView.getText().toString();
                mFname = mFnameView.getText().toString();
                mLname = mLnameView.getText().toString();
                mUserType = mUserTypeView.getSelectedItem().toString();

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

                    Toast.makeText(getActivity(), "Registration Successful.", Toast.LENGTH_LONG).show();

                }



            }
        });


        return v;

    }




}
