package com.example.ama.viewer.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ama.viewer.R
import com.example.ama.viewer.presentation.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_container, MainFragment.newInstance())
                .commit()
    }
}