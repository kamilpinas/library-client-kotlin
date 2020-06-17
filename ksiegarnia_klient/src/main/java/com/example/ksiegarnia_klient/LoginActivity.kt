package com.example.ksiegarnia_klient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private val baseUrl: String = "http://192.168.7.168:8080/"
    private lateinit var loginInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit
    private lateinit var login: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginInput = findViewById(R.id.loginInput)
        passwordInput = findViewById(R.id.passwordInput)
        //////////json
        retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)
        ////json

        loginButton.setOnClickListener {//TODO:: jak login i hasloo puste to pokazuje tyko login pusty XD
            emptyPasswordHint.setVisible(false)
            emptyLoginHint.setVisible(false)
            if (loginInput.text.toString() == "" && passwordInput.text.toString() == "") {
                emptyLoginHint.setVisible(true)
                emptyPasswordHint.setVisible(true)
            } else if (passwordInput.text.toString() == "") {
                emptyPasswordHint.setVisible(true)
            } else if (loginInput.text.toString() == "") {
                emptyLoginHint.setVisible(true)
            } else {
                login = loginInput.text.toString()
                password = passwordInput.text.toString()
                Log.d("wpisany login to: ", login)
                Log.d("wpisane haslo to: ", password)
                val newLogin = MyLogin(login, password, false)

                navView.setCheckedItem(R.id.nav_shoutbox)
                val sharedPreferences =
                    getSharedPreferences("shared preferences", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val default_login = "gość"
                editor.putString(
                    "user_login",
                    default_login
                )//TODO:: nie dziala zapisywanie loginu - zawsze pokazuje gość
                editor.apply()
                sendLogin(newLogin)
            }
        }
    }

    private fun sendLogin(MyLogin: MyLogin) {
        val call = jsonPlaceholderAPI.createPost(MyLogin)
        call.enqueue(object : Callback<MyLogin> {
            override fun onFailure(
                call: Call<MyLogin>,
                t: Throwable
            ) {
                Log.d("LOGOWANIE:",  "Login i haslo zle" )                //TODO:: to jest prymitywnie  bo teraz jak login i haslo sie nie zgadzaja to server zwraca null XD- jakby zrobic on bad http response ( w serverze na api controller - return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
               wrongLoginOrPasswordHint.setVisible(true)
            }

            override fun onResponse(
                call: Call<MyLogin>,
                response: Response<MyLogin>
            ) {
                finish()// przeniesienie do fragmentu ksiazii
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    return
                }
            }
        })
    }

    fun View.setVisible(visible: Boolean) {
        visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@LoginActivity, StartScreenActivity::class.java)
        finish()
        startActivity(intent)
    }
}