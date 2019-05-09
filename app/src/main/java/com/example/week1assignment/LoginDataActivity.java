package com.example.week1assignment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Stack;

public class LoginDataActivity extends AppCompatActivity {
    FragmentTransaction fragmentTransaction;
    private static final String TAG = LoginDataActivity.class.getSimpleName();
    Button btn_back_toolbar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_data);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_back_toolbar = findViewById(R.id.btn_toolbar_back);
        btn_back_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new RegisterAccountInfoFragment()/*CreateAccountFragment()*/);

        fragmentTransaction.commit();
    }



    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: Whoops! going back!");
        super.onBackPressed();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_below,R.anim.exit_to_above);
    }
}
