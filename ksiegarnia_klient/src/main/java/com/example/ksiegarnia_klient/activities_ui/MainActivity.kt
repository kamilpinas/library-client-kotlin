package com.example.ksiegarnia_klient.activities_ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

           val intent = Intent(this@MainActivity, StartScreenActivity::class.java)
        startActivity(intent)

    }
}

