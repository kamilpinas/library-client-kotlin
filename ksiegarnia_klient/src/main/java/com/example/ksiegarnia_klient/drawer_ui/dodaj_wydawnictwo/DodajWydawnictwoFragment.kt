package com.example.ksiegarnia_klient.drawer_ui.dodaj_wydawnictwo

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
import com.example.ksiegarnia_klient.activities_ui.baseUrl
import com.example.ksiegarnia_klient.activities_ui.isAdmin
import com.example.ksiegarnia_klient.activities_ui.isGuest
import com.example.ksiegarnia_klient.api_adapters.JsonPlaceholderAPI
import com.example.ksiegarnia_klient.api_data_structures.MyWydawnictwa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DodajWydawnictwoFragment : Fragment() {
    private lateinit var dodajWydawnictwoViewModel: DodajWydawnictwoViewModel
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit
    private lateinit var infoToast: Toast
    private lateinit var editTextNazwaWydawnictwa: EditText
    private lateinit var editTextNazwaMiasta: EditText
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

        if (!isAdmin && !isGuest) {
            makeToast("Zaloguj się jako admin, aby dodać nowe wydawnictwo")
        } else {
            form {
                input(editTextNazwaWydawnictwa, name = "nazwa_wydawnictwa") {
                    isNotEmpty().description("Podaj nazwe wydawnictwa!")
                    length().atMost(40).description("Nazwa wydawnictwa może mieć max 40 znaków")

                }
                input(editTextNazwaMiasta, name = "nazwa_miasta") {
                    isNotEmpty().description("Podaj miasto wydawnictwa!")
                    length().atMost(20).description("Miasto może mieć max 15 znaków")

                }
                submitWith(dodajWydawnictwoButton) { result ->
                    val dodajWydawnictwo =
                        MyWydawnictwa(
                            editTextNazwaWydawnictwa.text.toString(),
                            editTextNazwaMiasta.text.toString()
                        )
                    addWydawnictwo(dodajWydawnictwo)
                }
            }
        }
        return root
    }

    private fun addWydawnictwo(wydawnictwaData: MyWydawnictwa) {
        val call = jsonPlaceholderAPI.createPost(wydawnictwaData)
        call.enqueue(object : Callback<MyWydawnictwa> {
            override fun onFailure(
                call: Call<MyWydawnictwa>,
                t: Throwable
            ) {
                makeToast("Brak połączenia!")
                Log.d("tworzenie wydawnictwa:", " error")
            }

            override fun onResponse(
                call: Call<MyWydawnictwa>,
                response: Response<MyWydawnictwa>
            ) {
                if (!response.isSuccessful) {
                    makeToast("Podane wydawnictwo już istnieje w bazie!")
                    println("Code: " + response.code())
                    return
                } else {
                    makeToast("Dodano wydawnictwo!")
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

}

