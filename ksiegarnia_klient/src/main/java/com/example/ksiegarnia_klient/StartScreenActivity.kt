package com.example.ksiegarnia_klient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start_screen.*


class StartScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_start_screen)

        loginAsGuestButton.setOnClickListener {
            navView.setCheckedItem(R.id.nav_shoutbox)
            val sharedPreferences = getSharedPreferences("shared preferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val login = "gość"
            editor.putString("user_login", login)
            editor.apply()
            finish()
        }

        registerButton.setOnClickListener {
            val intent = Intent(this@StartScreenActivity, LoginActivity::class.java)
            finish()//zamknij start screen activity
            startActivity(intent)
        }


        registerButton.setOnClickListener {
            val intent = Intent(this@StartScreenActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

   override fun onBackPressed() {
       val intent = Intent(this@StartScreenActivity, StartScreenActivity::class.java)
       startActivity(intent)
   }
}
