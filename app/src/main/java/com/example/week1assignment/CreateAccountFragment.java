package com.example.week1assignment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFragment extends Fragment {
    Button btn_next;
    TextView err_email, err_password;
    EditText et_email, et_pass, et_rptpass;
    Boolean chk_email = false; Boolean chk_pass =false; Boolean chk_rptpass = false;

    Drawable iconimage;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +      //1 digit required
                    "(?=.*[a-z])" +      //1 lowecast required
                    "(?=.*[A-Z])" +      //1 Uppercase required
                    "(?=\\S+$)" +        //no white spaces
                    ".{8,}" +            //at least 8 characters
                    "$");



    private static final String TAG = "CreateAccountFragment";

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_create_account, container, false);

        //let the binding begin...
        et_email = rootView.findViewById(R.id.et_email);
        et_pass = rootView.findViewById(R.id.et_password);
        et_rptpass = rootView.findViewById(R.id.et_rptpass);
        err_email = rootView.findViewById(R.id.errtxt_email);
        err_password = rootView.findViewById(R.id.errtxt_password);
        btn_next = rootView.findViewById(R.id.btn_account_next);
        iconimage = getContext().getResources().getDrawable(R.drawable.tick2);




        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                chk_email = isValidEmail(et_email.getText().toString().trim());
                Log.d(TAG, "onFocusChange: chk_email: "+chk_email);
                isAllValid();
            }
        });

        et_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                chk_pass = isValidPassword(et_pass.getText().toString());
                Log.d(TAG, "onFocusChange: chk_pass: "+chk_pass);
                isAllValid();
            }
        });

        et_rptpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                chk_rptpass = isPassTheSame(et_pass.getText().toString(), et_rptpass.getText().toString());
                isAllValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Button was clicked!",Toast.LENGTH_SHORT).show();
                saveLoginData1();
            }
        });

        return rootView;
    }

    private void saveLoginData1() {
        String email = et_email.getText().toString().trim();
        String pass = et_pass.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        bundle.putString("password",pass);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,
                R.anim.enter_from_right,R.anim.exit_to_left);

        RegisterAccountInfoFragment register = new RegisterAccountInfoFragment();
        register.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_container,register);
        fragmentTransaction.addToBackStack(null).commit();
    }

    //Method to check the email
    private Boolean isValidEmail(String email) {

        String rgstrdEmail = "mikrsookwah@gmail.com";

        if (!email.isEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                if (!email.equals(rgstrdEmail)) {
                    //todo need to put a loop to check database for existing emails
                    et_email.setBackgroundResource(R.drawable.edit_text_border_valid);
                    et_email.setCompoundDrawablesWithIntrinsicBounds(null, null, iconimage, null);
                    err_email.setVisibility(View.GONE);
                    return true;
                } else {
                    et_email.setBackgroundResource(R.drawable.edit_text_border_invalid);
                    et_email.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    err_email.setText(R.string.err_email_already_exist);
                    err_email.setVisibility(View.VISIBLE);
                    return false;
                }
            }else {
                et_email.setBackgroundResource(R.drawable.edit_text_border_invalid);
                et_email.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                err_email.setText(R.string.err_email_not_valid);
                err_email.setVisibility(View.VISIBLE);
                return false;
            }
        }
        et_email.setBackgroundResource(R.color.colorWhiteFont);
        et_email.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        err_email.setVisibility(View.GONE);
        return false;

    }

    //Method to check the password
    private Boolean isValidPassword(String pass) {

        if (!pass.isEmpty()) {
            if (PASSWORD_PATTERN.matcher(pass).matches()) {
                et_pass.setBackgroundResource(R.drawable.edit_text_border_valid);
                et_pass.setCompoundDrawablesWithIntrinsicBounds(null, null, iconimage, null);
                err_password.setVisibility(View.GONE);
                return true;
            }else{
                et_pass.setBackgroundResource(R.drawable.edit_text_border_invalid);
                et_pass.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                err_password.setText(R.string.err_pass_not_valid);
                err_password.setVisibility(View.VISIBLE);
                return false;
            }
        }
        Log.d(TAG, "isValidEmail: reseting edit text password.");
        et_pass.setBackgroundResource(R.color.colorWhiteFont);
        et_pass.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        err_password.setVisibility(View.GONE);

        return false;
    }

    //see if both matches
    private boolean isPassTheSame(String pass, String rptpass) {
        if (!pass.isEmpty() && !rptpass.isEmpty()) {
            if (rptpass.equals(pass)) {
                et_rptpass.setBackgroundResource(R.drawable.edit_text_border_valid);
                et_rptpass.setCompoundDrawablesWithIntrinsicBounds(null, null, iconimage, null);

                err_password.setVisibility(View.GONE);
                return true;
            } else {
                et_rptpass.setBackgroundResource(R.drawable.edit_text_border_invalid);
                et_rptpass.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                if(chk_pass) {
                    err_password.setText(R.string.err_pass_not_match);
                }else{
                    err_password.setText(R.string.err_pass_not_valid);
                }
                err_password.setVisibility(View.VISIBLE);
                return false;
            }
        }
        et_rptpass.setBackgroundResource(R.color.colorWhiteFont);
        et_rptpass.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);


        err_password.setVisibility(View.GONE);
        return false;
    }

    //Check if all are valid
    private void isAllValid() {
        if (chk_email && chk_pass && chk_rptpass) {
            btn_next.setEnabled(true);
            btn_next.setBackgroundResource(R.drawable.gradient_button_background);
            Log.d(TAG, "isAllValid: All Three Values are true");
        } else {
            btn_next.setEnabled(false);
            btn_next.setBackgroundResource(R.drawable.gradient_button_background_inactive);
            Log.d(TAG, "isAllValid: At least one is not valid...");
        }
    }

}
