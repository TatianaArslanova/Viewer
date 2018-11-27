package com.example.ama.viewer.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ama.viewer.R;
import com.example.ama.viewer.presentation.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_container, ProfileFragment.newInstance())
                    .commit();
        }
    }
}
