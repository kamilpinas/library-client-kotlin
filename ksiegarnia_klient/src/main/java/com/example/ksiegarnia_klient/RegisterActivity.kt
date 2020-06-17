package com.example.ksiegarnia_klient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.vvalidator.form
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    private val baseUrl: String = "http://192.168.7.168:8080/"
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit
    private var control:Int = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)

            form{
                input(R.id.editTextLogin,name="login"){
                    isNotEmpty().description("Podaj login !")
                    length().atLeast(6).description("Login musi zawierac minimum 6 znaków")
                }
                input(R.id.editTextPassword,name="haslo"){
                    isNotEmpty().description("Podaj hasło !")
                    length().atLeast(6).description("Hasło musi zawierac minimum 6 znaków")
                }
                input(R.id.editTextImie,name="imię"){
                    isNotEmpty().description("Podaj imię !")
                }
                input(R.id.editTextNazwisko,name="nazwisko"){
                    isNotEmpty().description("Podaj nazwisko !")
                }
                input(R.id.editTextUlica,name="ulica"){
                    isNotEmpty().description("Podaj ulicę !")
                }
                input(R.id.editTextMiejscowosc,name="miejscowosc"){
                    isNotEmpty().description("Podaj miejscowość !")
                }
                input(R.id.editTextNrDomu,name="NumerDomu"){
                    isNotEmpty().description("Podaj numer domu !")
                    isNumber()
                    length().atMost(7)
                }
                input(R.id.editTextTelefon,name="telefon"){
                    isNotEmpty().description("Podaj numer telefonu !")
                    isNumber()
                    length().atLeast(9)
                }
                input(R.id.editTextKodPocztowy,name="Kod Pocztowy"){
                    isNotEmpty().description("Podaj kod pocztowy !")
                    length().atLeast(5)
                    length().atMost(6)
                }

                submitWith(R.id.registerButton){result ->
                    val newRegister = MyRegister(editTextNazwisko.text.toString(),editTextImie.text.toString(),editTextKodPocztowy.text.toString(),editTextMiejscowosc.text.toString(),editTextUlica.text.toString(),editTextNrDomu.text.toString(),editTextTelefon.text.toString(),editTextLogin.text.toString(),editTextPassword.text.toString())
                    sendRegister(newRegister)
                }
            }
    }


    private fun sendRegister(MyRegister: MyRegister) {
        val call = jsonPlaceholderAPI.createPost(MyRegister)
        call.enqueue(object : Callback<MyRegister> {
            override fun onFailure(
                call: Call<MyRegister>,
                t: Throwable
            ) {
                Toast.makeText(this@RegisterActivity,"Brak połączenia!",Toast.LENGTH_SHORT).show()
                Log.d("rejestracja:",  " error" )
            }
            override fun onResponse(
                call: Call<MyRegister>,
                response: Response<MyRegister>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity,"Podany login istnieje!",Toast.LENGTH_SHORT).show()
                    println("Code: " + response.code())
                    return
                }
                else
                {
                    Toast.makeText(this@RegisterActivity,"Zarejestrowano pomyślnie!",Toast.LENGTH_SHORT).show()
                    finish()// przeniesienie do fragmentu ksiazii
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

