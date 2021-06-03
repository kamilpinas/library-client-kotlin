package com.example.ksiegarnia_klient.activities_ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.vvalidator.form
import com.example.ksiegarnia_klient.R
import com.example.ksiegarnia_klient.api_adapters.JsonPlaceholderAPI
import com.example.ksiegarnia_klient.api_data_structures.ClientData
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit

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

        form {
            input(R.id.editTextLogin, name = "login") {
                isNotEmpty().description("Podaj login !")
                length().atLeast(6).description("Login musi zawierac minimum 6 znaków")
                length().atMost(20).description("Login może mieć max 20 znaków")

            }
            input(R.id.editTextPassword, name = "haslo") {
                isNotEmpty().description("Podaj hasło !")
                length().atLeast(6).description("Hasło musi zawierac minimum 6 znaków")
                length().atMost(10).description("Hasło może mieć max 10 znaków")

            }
            input(R.id.editTextImie, name = "imię") {
                isNotEmpty().description("Podaj imię !")
                length().atMost(40).description("Imię może mieć max 40 znaków")

            }
            input(R.id.editTextNazwisko, name = "nazwisko") {
                isNotEmpty().description("Podaj nazwisko !")
                length().atMost(60).description("Nazwisko może mieć max 60 znaków")

            }
            input(R.id.editTextUlica, name = "ulica") {
                isNotEmpty().description("Podaj ulicę !")
                length().atMost(50).description("Ulica może mieć max 50 znaków")

            }
            input(R.id.editTextMiejscowosc, name = "miejscowosc") {
                isNotEmpty().description("Podaj miejscowość !")
                length().atMost(50).description("Miejscowosc może mieć max 50 znaków")

            }
            input(R.id.editTextNrDomu, name = "NumerDomu") {
                isNotEmpty().description("Podaj numer domu !")
                isNumber()
                length().atMost(7)
            }
            input(R.id.editTextTelefon, name = "telefon") {
                isNotEmpty().description("Podaj numer telefonu !")
                isNumber()
                length().atLeast(9)
                length().atMost(12).description("Telefon może mieć max 12 znaków")

            }
            input(R.id.editTextKodPocztowy, name = "Kod Pocztowy") {
                isNotEmpty().description("Podaj kod pocztowy !")
                length().atLeast(5)
                length().atMost(6).description("Kod pocztowy może mieć max 6 znaków")
            }

            submitWith(R.id.registerButton) { result ->
                val newRegister =
                    ClientData(
                        editTextNazwisko.text.toString(),
                        editTextImie.text.toString(),
                        editTextKodPocztowy.text.toString(),
                        editTextMiejscowosc.text.toString(),
                        editTextUlica.text.toString(),
                        editTextNrDomu.text.toString(),
                        editTextTelefon.text.toString(),
                        editTextLogin.text.toString(),
                        editTextPassword.text.toString()
                    )
                sendRegister(newRegister)
            }
        }
    }

    private fun sendRegister(ClientData: ClientData) {
        val call = jsonPlaceholderAPI.clientRegister(ClientData)
        call.enqueue(object : Callback<ClientData> {
            override fun onFailure(
                call: Call<ClientData>,
                t: Throwable
            ) {
                Toast.makeText(this@RegisterActivity, "Brak połączenia!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ClientData>,
                response: Response<ClientData>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Podany login istnieje!",
                        Toast.LENGTH_SHORT
                    ).show()
                    println("Code: " + response.code())
                    return
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Zarejestrowano pomyślnie! Teraz możesz się zalogować.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@RegisterActivity, StartScreenActivity::class.java)
                    finish()//zamknij start screen activity
                    startActivity(intent)
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

