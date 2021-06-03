package com.example.ksiegarnia_klient.drawer_ui.dodaj_autora

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
import com.example.ksiegarnia_klient.api_data_structures.MyAutor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DodajAutoraFragment : Fragment() {
    private lateinit var dodajAutoraViewModel: DodajAutoraViewModel
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit
    private lateinit var infoToast: Toast
    private lateinit var editTextImie: EditText
    private lateinit var editTextNazwisko: EditText
    private lateinit var editTextJezyk: EditText
    private lateinit var editTextNarodowosc: EditText
    private lateinit var editTextOkresTworzenia: EditText
    private lateinit var dodajAutoraButton: Button
    private lateinit var dodajAutoraView: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dodajAutoraViewModel =
            ViewModelProviders.of(this).get(DodajAutoraViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dodaj_autora, container, false)

        editTextImie = root.findViewById(R.id.editTextImie)
        editTextNazwisko = root.findViewById(R.id.editTextNazwisko)
        editTextImie = root.findViewById(R.id.editTextImie)
        editTextNarodowosc = root.findViewById(R.id.editTextNarodowosc)
        editTextOkresTworzenia = root.findViewById(R.id.editTextOkresTworzenia)
        editTextJezyk = root.findViewById(R.id.editTextJezyk)

        dodajAutoraButton = root.findViewById(R.id.dodajAutoraButton)

        dodajAutoraView = root.findViewById(R.id.dodajAutoraView)
        retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)


        if (!isAdmin && !isGuest) {
            makeToast("Zaloguj się jako admin, aby dodac nowego autora")
        } else {
            form {
                input(editTextNarodowosc, name = "narodowosc") {
                    isNotEmpty().description("Podaj Narodowość !")
                    length().atMost(30).description("Narodowość może mieć max 30 znaków")
                }
                input(editTextOkresTworzenia, name = "okres_tworzenia") {
                    isNotEmpty().description("Podaj okres tworzenia !")
                    length().atMost(35).description("Okres tworzenia może mieć max 35znaków")

                }
                input(editTextImie, name = "imię") {
                    isNotEmpty().description("Podaj imię !")
                    length().atMost(30).description("Imię może mieć max 30 znaków")

                }
                input(editTextNazwisko, name = "nazwisko") {
                    isNotEmpty().description("Podaj nazwisko !")
                    length().atMost(50).description("Nazwisko może mieć max 50 znaków")

                }
                input(editTextJezyk, name = "Język") {
                    isNotEmpty().description("Podaj język !")
                    length().atMost(30).description("Język może mieć max 30 znaków")
                }

                submitWith(dodajAutoraButton) { result ->
                    val autorData =
                        MyAutor(
                            editTextNazwisko.text.toString(),
                            editTextImie.text.toString(),
                            editTextNarodowosc.text.toString(),
                            editTextOkresTworzenia.text.toString(),
                            editTextJezyk.text.toString()
                        )
                    addAutor(autorData)
                }
            }
        }
        return root
    }

    private fun addAutor(MyAutor: MyAutor) {
        val call = jsonPlaceholderAPI.createAuthor(MyAutor)
        call.enqueue(object : Callback<MyAutor> {
            override fun onFailure(
                call: Call<MyAutor>,
                t: Throwable
            ) {
                makeToast("Brak połączenia!")
                Log.d("nowy autor:", " error")
            }

            override fun onResponse(
                call: Call<MyAutor>,
                response: Response<MyAutor>
            ) {
                if (!response.isSuccessful) {
                    makeToast("Podany autor już istnieje w bazie!")
                    println("Code: " + response.code())
                    return
                } else {
                    makeToast("Zaktualizowano autora!")
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

