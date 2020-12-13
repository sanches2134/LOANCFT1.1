package com.example.loancft11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loancft11.models.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commit()
        }
    }
}