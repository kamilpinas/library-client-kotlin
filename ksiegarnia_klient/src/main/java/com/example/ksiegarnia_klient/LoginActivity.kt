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
var isAdmin: Boolean = false

class LoginActivity : AppCompatActivity() {
    // private val baseUrl: String = "http://192.168.7.168:8080/" //TODO:: PINAS
    private val baseUrl: String = "http://192.168.0.106:8080/" //TODO:: BUDZICZEK
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

        loginButton.setOnClickListener {
            //TODO:: jak login i hasloo puste to pokazuje tyko login pusty XD
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
                val newLogin = MyLogin(login, password)

                val sharedPreferences =
                    getSharedPreferences("shared preferences", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val default_login = "gosc_domyślny"
                editor.putString(
                    "user_login",
                    default_login
                )
                editor.apply()
                sendLogin(newLogin)
            }
        }
    }

    private fun sendLogin(MyLogin: MyLogin) {
        val call = jsonPlaceholderAPI.createPost(MyLogin)
        val callAdmin = jsonPlaceholderAPI.createPostAdmin(MyLogin)

        var notAdminCheckClient: Boolean
        notAdminCheckClient = false
        callAdmin.enqueue(object : Callback<MyLogin> {
            override fun onFailure(
                call: Call<MyLogin>,
                t: Throwable
            ) {
                notAdminCheckClient = true
                Log.d("DUIPA", notAdminCheckClient.toString())
                isAdmin=false
                return
            }

            override fun onResponse(
                call: Call<MyLogin>,
                response: Response<MyLogin>
            ) {
                isAdmin=true
                saveLogin()
                val intent = Intent(this@LoginActivity, DrawerActivity::class.java)
                startActivity(intent)
                Log.d("Jestes adminem lol", "!!!!")
                finish()// przeniesienie do fragmentu ksiazii

                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    return
                }
            }
        })

        //if (notAdminCheckClient) {
            call.enqueue(object : Callback<MyLogin> {
                override fun onFailure(
                    call: Call<MyLogin>,
                    t: Throwable
                ) {
                    Log.d(
                        "LOGOWANIE:",
                        "Login i haslo zle"
                    )                //TODO:: to jest prymitywnie  bo teraz jak login i haslo sie nie zgadzaja to server zwraca null XD- jakby zrobic on bad http response ( w serverze na api controller - return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    wrongLoginOrPasswordHint.setVisible(true)
                }

                override fun onResponse(
                    call: Call<MyLogin>,
                    response: Response<MyLogin>
                ) {
                    Log.d("jestes klientem lol", "!!!!!!!!!!!!!!!!!!!!!")
                    saveLogin()
                    val intent = Intent(this@LoginActivity, DrawerActivity::class.java)
                    startActivity(intent)
                    finish()// przeniesienie do fragmentu ksiazii

                    if (!response.isSuccessful) {
                        println("Code: " + response.code())
                        return
                    }
                }
            })
      //  }
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

    fun saveLogin() {
        val sharedPreferences = getSharedPreferences("shared preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val login = loginInput.text.toString()
        editor.putString("user_login", login)
        editor.apply()
    }


}