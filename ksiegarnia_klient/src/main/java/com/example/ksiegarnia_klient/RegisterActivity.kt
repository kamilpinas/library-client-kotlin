package com.example.ksiegarnia_klient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    private val baseUrl: String = "http://192.168.7.168:8080/"
    private lateinit var loginInput: EditText
    private lateinit var hasloInput: EditText
    private lateinit var imieInput: EditText
    private lateinit var nazwiskoInput: EditText
    private lateinit var miejscowoscInput: EditText
    private lateinit var ulicaInput: EditText
    private lateinit var nrDomuInput: EditText
    private lateinit var telefonInput: EditText
    private lateinit var kodPocztowyInput: EditText

    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        loginInput=findViewById(R.id.editTextLogin)
        hasloInput=findViewById(R.id.editTextPassword)
        imieInput=findViewById(R.id.editTextImie)
        nazwiskoInput=findViewById(R.id.editTextNazwisko)
        miejscowoscInput=findViewById(R.id.editTextUlica)
        ulicaInput=findViewById(R.id.editTextUlica)
        nrDomuInput=findViewById(R.id.editTextNrDomu)
        telefonInput=findViewById(R.id.editTextTelefon)
        kodPocztowyInput=findViewById(R.id.editTextKodPocztowy)

        retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)

        registerButton.setOnClickListener {
            val newRegister = MyRegister(nazwiskoInput.text.toString(),imieInput.text.toString(),kodPocztowyInput.text.toString(),miejscowoscInput.text.toString(),ulicaInput.text.toString(),nrDomuInput.text.toString(),telefonInput.text.toString(),loginInput.text.toString(),hasloInput.text.toString())
            sendRegister(newRegister)
        }
    }


    private fun sendRegister(MyRegister: MyRegister) {
        val call = jsonPlaceholderAPI.createPost(MyRegister)
        call.enqueue(object : Callback<MyRegister> {
            override fun onFailure(
                call: Call<MyRegister>,
                t: Throwable
            ) {
                Log.d("rejestracja:",  " error" )
            }

            override fun onResponse(
                call: Call<MyRegister>,
                response: Response<MyRegister>
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
        val intent = Intent(this@RegisterActivity, StartScreenActivity::class.java)
        finish()
        startActivity(intent)
    }
}

