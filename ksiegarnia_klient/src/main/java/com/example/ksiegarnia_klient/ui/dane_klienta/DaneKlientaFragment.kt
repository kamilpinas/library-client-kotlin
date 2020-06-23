package com.example.ksiegarnia_klient.ui.dane_klienta

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
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
import com.afollestad.vvalidator.form
import com.example.ksiegarnia_klient.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DaneKlientaFragment : Fragment() {
    private lateinit var daneKlientaViewModel: DaneKlientaViewModel
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit
    private lateinit var clientData: Array<ClientData>
    var activity: Activity? = getActivity()
    private lateinit var infoToast: Toast

    private lateinit var editTextLogin: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextImie: EditText
    private lateinit var editTextNazwisko: EditText
    private lateinit var editTextKodPocztowy: EditText
    private lateinit var editTextUlica: EditText
    private lateinit var editTextMiejscowosc: EditText
    private lateinit var editTextNrDomu: EditText
    private lateinit var editTextTelefon: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var alertDialog: AlertDialog

    private lateinit var daneKlientaView: LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        daneKlientaViewModel =
            ViewModelProviders.of(this).get(DaneKlientaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dane_klienta, container, false)




        editTextLogin = root.findViewById(R.id.editTextLogin)
        editTextPassword = root.findViewById(R.id.editTextPassword)
        editTextImie = root.findViewById(R.id.editTextImie)
        editTextNazwisko = root.findViewById(R.id.editTextNazwisko)
        editTextKodPocztowy = root.findViewById(R.id.editTextKodPocztowy)
        editTextTelefon = root.findViewById(R.id.editTextTelefon)
        editTextUlica = root.findViewById(R.id.editTextUlica)
        editTextMiejscowosc = root.findViewById(R.id.editTextMiejscowosc)
        editTextNrDomu = root.findViewById(R.id.editTextNrDomu)
        updateButton = root.findViewById(R.id.updateButton)
        deleteButton = root.findViewById(R.id.deleteButton)

        daneKlientaView = root.findViewById(R.id.daneKlientaView)
        retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)

        deleteButton.setOnClickListener {

        }



        deleteButton.setOnClickListener {
            showDialog()
        }

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
                    val updateData = ClientData(
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
            updateButton.visibility = View.GONE
            deleteButton.visibility = View.GONE

            Log.d("POBIERANIE DANYCH:::", "JESTES GOSCIEM LUB ADMINEM")
            makeToast("Zaloguj się jako klient, aby zobaczyć i edytować swoje dane")
        }
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
                editTextLogin.setText(clientData.get(0).login.toString())
                editTextPassword.setText(clientData.get(0).haslo.toString())
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


    fun deleteClient() {
        val call = jsonPlaceholderAPI.deleteClient(currentUserLogin, currentUserPassowrd)
        call.enqueue(object : Callback<MyLogin> {
            override fun onFailure(
                call: Call<MyLogin>,
                t: Throwable
            ) {
                //blad jakis lol
                return

            }

            override fun onResponse(
                call: Call<MyLogin>,
                response: Response<MyLogin>
            ) {
                makeToast("Pomyślnie skasowano konto!")
                getActivity()?.finishAffinity();
                val intent = Intent(getActivity(), StartScreenActivity::class.java)
                startActivity(intent)
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    return
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
                deleteClient()

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

