package com.example.ksiegarnia_klient.drawer_ui.dodaj_ksiazke

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.ksiegarnia_klient.*
import com.example.ksiegarnia_klient.activities_ui.baseUrl
import com.example.ksiegarnia_klient.api_adapters.JsonPlaceholderAPI
import com.example.ksiegarnia_klient.api_data_structures.ClientData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DodajKsiazkeFragment : Fragment() {
    private lateinit var dodajKsiazkeViewModel: DodajKsiazkeViewModel
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit
    private lateinit var clientData: Array<ClientData>
    var activity: Activity? = getActivity()
    private lateinit var infoToast: Toast

    private lateinit var editTextTytulKsiazki: EditText
    private lateinit var editTextTematKsiazki: EditText
    private lateinit var editTextJezykKsiazki: EditText
    private lateinit var editTextRokWydania: EditText
    private lateinit var editTextOpisKsiazki: EditText
    private lateinit var dostepnoscCheckBox: CheckBox
    private lateinit var spinnerAutor: Spinner
    private lateinit var spinnerWydawnictwo: Spinner
    private lateinit var dodajKsiazkeButton: Button
    private lateinit var dodajKsiazkeView: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dodajKsiazkeViewModel =
            ViewModelProviders.of(this).get(DodajKsiazkeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dodaj_ksiazke, container, false)

        editTextTytulKsiazki = root.findViewById(R.id.editTextTytulKsiazki)
        editTextTematKsiazki = root.findViewById(R.id.editTextTematKsiazki)
        editTextJezykKsiazki = root.findViewById(R.id.editTextJezykKsiazki)
        editTextRokWydania = root.findViewById(R.id.editTextRokWydania)
        editTextOpisKsiazki = root.findViewById(R.id.editTextOpisKsiazki)
        dostepnoscCheckBox = root.findViewById(R.id.dostepnoscCheckBox)
        spinnerAutor = root.findViewById(R.id.spinnerAutor)
        spinnerWydawnictwo = root.findViewById(R.id.spinnerWydawnictwo)
        dodajKsiazkeButton = root.findViewById(R.id.dodajWydawnictwoButton)

        dodajKsiazkeView = root.findViewById(R.id.dodajKsiazkeView)
        retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)

        dodajKsiazkeButton.setOnClickListener {
            showDialog()
        }
/*
        if (!isAdmin && !isGuest) {
            getAndShowClientData() // pobieranie danych klienta

            form {
                input(editTextTytulKsiazki, name = "login") {
                    isNotEmpty().description("Podaj login !")
                    length().atLeast(6).description("Login musi zawierac minimum 6 znaków")
                }
                input(editTextTematKsiazki, name = "haslo") {
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
                            editTextTytulKsiazki.text.toString(),
                            editTextTematKsiazki.text.toString()
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

    /*
        fun getAndShowClientData() {
            val call = jsonPlaceholderAPI.getClientArray(
                currentUserLogin,
                currentUserPassowrd
            )

            call!!.enqueue(object : Callback<Array<ClientData>?> {
                override fun onResponse(
                    call: Call<Array<ClientData>?>,
                    response: Response<Array<ClientData>?>
                ) {
                    if (!response.isSuccessful) {
                        println("Code: " + response.code())
                        return
                    }
                    clientData = response.body()!!
                    editTextTytulKsiazki.setText(clientData.get(0).login.toString())
                    editTextTematKsiazki.setText(clientData.get(0).haslo.toString())
                    editTextImie.setText(clientData.get(0).imie.toString())
                    editTextNazwisko.setText(clientData.get(0).nazwisko.toString())
                    editTextTelefon.setText(clientData.get(0).telefon.toString())
                    editTextKodPocztowy.setText(clientData.get(0).kodPocztowy.toString())
                    editTextMiejscowosc.setText(clientData.get(0).miejscowosc.toString())
                    editTextNrDomu.setText(clientData.get(0).nrDomu.toString())
                    editTextUlica.setText(clientData.get(0).ulica.toString())
                }

                override fun onFailure(
                    call: Call<Array<ClientData>?>,
                    t: Throwable
                ) {
                    println(t.message)
                }
            })
        }
    */
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

