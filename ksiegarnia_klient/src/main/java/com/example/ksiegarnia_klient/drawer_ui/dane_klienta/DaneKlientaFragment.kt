package com.example.ksiegarnia_klient.drawer_ui.dane_klienta

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
import com.example.ksiegarnia_klient.activities_ui.*
import com.example.ksiegarnia_klient.api_adapters.JsonPlaceholderAPI
import com.example.ksiegarnia_klient.api_data_structures.ClientData
import com.example.ksiegarnia_klient.api_data_structures.MyLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

/**
 * Client Data fragment - Client data details screen
 *
 * @constructor Create empty Client data fragment
 */
class DaneKlientaFragment : Fragment() {
    private lateinit var daneKlientaViewModel: DaneKlientaViewModel
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit
    private lateinit var clientData: ClientData
    var activity: Activity? = getActivity()
    private lateinit var infoToast: Toast
    private lateinit var editTextLogin: EditText
    private var id by Delegates.notNull<Long>()
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
        updateButton = root.findViewById(R.id.aktualizujKlientaButton)
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
            showDialog()
        }

        if (!isAdmin && !isGuest) {
            getAndShowClientData() // pobieranie danych klienta
            editTextLogin.isEnabled=false
            editTextImie.isEnabled=false
            editTextNazwisko.isEnabled=false
            form {

                input(editTextPassword, name = "haslo") {
                    isNotEmpty().description("Podaj hasło !")
                    length().atLeast(6).description("Hasło musi zawierac minimum 6 znaków")
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
                            id,
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

    /**
     * Send client data to server to update in database
     *
     * @param ClientData
     */
    private fun sendUpdate(ClientData: ClientData) {
        val call = jsonPlaceholderAPI.updateClient(ClientData)
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

    /**
     * Get and show client data
     *
     */
    fun getAndShowClientData() {
        val call = jsonPlaceholderAPI.getClientArray(
            currentUserLogin,
            currentUserPassowrd
        )

        call!!.enqueue(object : Callback<ClientData> {
            override fun onResponse(
                call: Call<ClientData>,
                response: Response<ClientData>
            ) {
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    return
                }
                clientData = response.body()!!
                id = clientData.clientId!!
                editTextLogin.setText(clientData.login.toString())
                editTextPassword.setText(clientData.password.toString())
                editTextImie.setText(clientData.name.toString())
                editTextNazwisko.setText(clientData.surname.toString())
                editTextTelefon.setText(clientData.phoneNumber.toString())
                editTextKodPocztowy.setText(clientData.zipCode.toString())
                editTextMiejscowosc.setText(clientData.city.toString())
                editTextNrDomu.setText(clientData.houseNumber.toString())
                editTextUlica.setText(clientData.street.toString())

            }

            override fun onFailure(
                call: Call<ClientData>,
                t: Throwable
            ) {
                println(t.message)
            }
        })
    }

    /**
     * Delete client
     *
     */
    fun deleteClient() {
        val call = jsonPlaceholderAPI.deleteClient(
            currentUserLogin,
            currentUserPassowrd
        )
        call.enqueue(object : Callback<MyLogin> {
            override fun onFailure(
                call: Call<MyLogin>,
                t: Throwable
            ) {
                return
            }

            override fun onResponse(
                call: Call<MyLogin>,
                response: Response<MyLogin>
            ) {

                if (response.isSuccessful) {
                    makeToast("Pomyślnie skasowano konto!")
                    getActivity()?.finishAffinity();
                    val intent = Intent(getActivity(), StartScreenActivity::class.java)
                    startActivity(intent)
                    return
                }
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    makeToast("Istnieja wypozyczone ksiazki, nie mozna usunac!")
                    return
                }
            }
        })
    }

    /**
     * Make toast
     *
     * @param myToastText
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

    /**
     * Show dialog
     *
     */
    fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage("Czy na pewno chcesz nieodwracalnie skasować konto?")
        dialogBuilder.setPositiveButton("Tak",
            { dialog, whichButton ->
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

