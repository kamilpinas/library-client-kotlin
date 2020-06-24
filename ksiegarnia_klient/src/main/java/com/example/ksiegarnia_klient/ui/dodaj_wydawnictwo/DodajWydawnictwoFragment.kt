package com.example.ksiegarnia_klient.ui.dodaj_wydawnictwo

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.ksiegarnia_klient.*
import com.example.ksiegarnia_klient.api_adapters.JsonPlaceholderAPI
import com.example.ksiegarnia_klient.api_data_structures.ClientData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DodajWydawnictwoFragment : Fragment() {
    private lateinit var dodajWydawnictwoViewModel: DodajWydawnictwoViewModel
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit
    private lateinit var clientData: Array<ClientData>
    var activity: Activity? = getActivity()
    private lateinit var infoToast: Toast
    private lateinit var editTextNazwaWydawnictwa: EditText
    private lateinit var editTextNazwaMiasta: EditText
    private lateinit var alertDialog: AlertDialog
    private lateinit var dodajWydawnictwoView: LinearLayout
    private lateinit var dodajWydawnictwoButton: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dodajWydawnictwoViewModel =
            ViewModelProviders.of(this).get(DodajWydawnictwoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dodaj_wydawnictwo, container, false)

        editTextNazwaWydawnictwa = root.findViewById(R.id.editTextNazwaWydawnictwa)
        editTextNazwaMiasta = root.findViewById(R.id.editTextNazwaMiasta)

        dodajWydawnictwoButton = root.findViewById(R.id.dodajWydawnictwoButton)

        dodajWydawnictwoView = root.findViewById(R.id.dodajWydawnictwoView)
        retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)

        dodajWydawnictwoButton.setOnClickListener {
            showDialog()
        }

/*
        if (!isAdmin && !isGuest) {
            getAndShowClientData() // pobieranie danych klienta

            form {
                input(editTextLogin, name = "login") {
                    isNotEmpty().description("Podaj login !")
                    length().atLeast(6).description("Login musi zawierac minimum 6 znaków")
                }
                input(editTextPassword, name = "haslo") {
                    isNotEmpty().description("Podaj hasło !")
                    length().atLeast(6).description("Hasło musi zawierac minimum 6 znaków")
                }
                input(editTextImie, name = "imię") {
                    isNotEmpty().description("Podaj imię !")
                }
                input(editTextNazwisko, name = "nazwisko") {
                    isNotEmpty().description("Podaj nazwisko !")
                }
                input(editTextUlica, name = "ulica") {
                    isNotEmpty().description("Podaj ulicę !")
                }
                input(editTextMiejscowosc, name = "miejscowosc") {
                    isNotEmpty().description("Podaj miejscowość !")
                }
                input(editTextNrDomu, name = "NumerDomu") {
                    isNotEmpty().description("Podaj numer domu !")
                    isNumber()
                    length().atMost(7)
                }
                input(editTextTelefon, name = "telefon") {
                    isNotEmpty().description("Podaj numer telefonu !")
                    isNumber()
                    length().atLeast(9)
                }
                input(editTextKodPocztowy, name = "Kod Pocztowy") {
                    isNotEmpty().description("Podaj kod pocztowy !")
                    length().atLeast(5)
                    length().atMost(6)
                }

                submitWith(updateButton) { result ->
                    val updateData =
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
                    sendUpdate(updateData)
                }
            }
        } else {


            Log.d("POBIERANIE DANYCH:::", "JESTES GOSCIEM LUB ADMINEM")
            makeToast("Zaloguj się jako klient, aby zobaczyć i edytować swoje dane")
        }*/
        return root
    }

    private fun sendUpdate(ClientData: ClientData) {
        val call = jsonPlaceholderAPI.createPut(ClientData)
        call.enqueue(object : Callback<ClientData> {
            override fun onFailure(
                call: Call<ClientData>,
                t: Throwable
            ) {
                makeToast("Brak połączenia!")
                Log.d("rejestracja:", " error")
            }

            override fun onResponse(
                call: Call<ClientData>,
                response: Response<ClientData>
            ) {
                if (!response.isSuccessful) {
                    makeToast("Podany login już istnieje w bazie!")
                    println("Code: " + response.code())
                    return
                } else {
                    makeToast("Zaktualizowano dane !.")
                }
            }
        })
    }


    fun makeToast(myToastText: String) {
        infoToast = Toast.makeText(
            context,
            myToastText,
            Toast.LENGTH_SHORT
        )
        infoToast.setGravity(Gravity.TOP, 0, 200)
        infoToast.show()
    }

    fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage("Czy na pewno chcesz nieodwracalnie skasować konto?")
        dialogBuilder.setPositiveButton("Tak",
            DialogInterface.OnClickListener { dialog, whichButton ->
            }
        )
        dialogBuilder.setNegativeButton("Nie",
            DialogInterface.OnClickListener { dialog, whichButton ->
            })
        val b = dialogBuilder.create()
        if (b.toString() == "tak") {
            Log.d("dasdas", "DSADASDASD")
        }
        b.show()
    }
}

