package com.example.week1assignment;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private FrameLayout fragmentContainer;
    Button btn_create_account;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: everything is initialized!");

        fragmentContainer = findViewById(R.id.fragment_container);
        btn_create_account = findViewById(R.id.btn_create_account);

        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccount();
            }
        });
    }

    private void openCreateAccount() {
        Log.d(TAG, "openCreateAccount: Button Clicked! Lets go to login!");
        Intent intent = new Intent(this,LoginDataActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_above,R.anim.exit_to_below);
    }
}
