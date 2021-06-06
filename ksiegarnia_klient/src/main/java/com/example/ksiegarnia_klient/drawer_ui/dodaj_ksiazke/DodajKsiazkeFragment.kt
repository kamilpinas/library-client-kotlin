package com.example.ksiegarnia_klient.drawer_ui.dodaj_ksiazke

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.afollestad.vvalidator.form
import com.example.ksiegarnia_klient.*
import com.example.ksiegarnia_klient.activities_ui.baseUrl
import com.example.ksiegarnia_klient.activities_ui.isAdmin
import com.example.ksiegarnia_klient.activities_ui.isGuest
import com.example.ksiegarnia_klient.api_adapters.JsonPlaceholderAPI
import com.example.ksiegarnia_klient.api_data_structures.MyAutor
import com.example.ksiegarnia_klient.api_data_structures.MyBooks
import com.example.ksiegarnia_klient.api_data_structures.MyCategory
import com.example.ksiegarnia_klient.api_data_structures.MyWydawnictwa
import kotlinx.android.synthetic.main.fragment_dodaj_ksiazke.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.ArrayAdapter as ArrayAdapter1

class DodajKsiazkeFragment : Fragment() {
    private lateinit var dodajKsiazkeViewModel: DodajKsiazkeViewModel
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit
    private lateinit var infoToast: Toast
    private lateinit var autorData: Array<MyAutor>
    private lateinit var wydawnictwaData: Array<MyWydawnictwa>
    private lateinit var categoryData: Array<MyCategory>
    private lateinit var editTextTytulKsiazki: EditText
    private lateinit var editTextJezykKsiazki: EditText
    private lateinit var editTextRokWydania: EditText
    private lateinit var editTextOpisKsiazki: EditText
    private lateinit var dostepnoscCheckBox: CheckBox
    private lateinit var spinnerAutor: Spinner
    private lateinit var spinnerWydawnictwo: Spinner
    private lateinit var spinnerCategory: Spinner
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
        editTextJezykKsiazki = root.findViewById(R.id.editTextJezykKsiazki)
        editTextRokWydania = root.findViewById(R.id.editTextRokWydania)
        editTextOpisKsiazki = root.findViewById(R.id.editTextOpisKsiazki)
        dostepnoscCheckBox = root.findViewById(R.id.dostepnoscCheckBox)
        spinnerAutor = root.findViewById(R.id.spinnerAutor)
        spinnerWydawnictwo = root.findViewById(R.id.spinnerWydawnictwo)
        spinnerCategory = root.findViewById(R.id.spinnerCategory)
        dodajKsiazkeButton = root.findViewById(R.id.dodajKsiazkeButton)

        dodajKsiazkeView = root.findViewById(R.id.dodajKsiazkeView)
        retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)
        if (!isAdmin && !isGuest) {
            Log.d("POBIERANIE DANYCH:::", "JESTES GOSCIEM LUB ADMINEM")
            makeToast("Zaloguj się jako admin, aby dodac ksiazke")
        } else {
            getWydawnictwa()
            getAutors()
            getCategories()
            form {
                input(editTextTytulKsiazki, name = "tytul") {
                    isNotEmpty().description("Podaj tytuł książki !")
                    length().atMost(100).description("Tytuł może mieć max 100 znaków")

                }
                input(editTextJezykKsiazki, name = "jezyk_ksiazki") {
                    isNotEmpty().description("Podaj język ksiazki !")
                    length().atMost(15).description("Język może mieć max 15 znaków")

                }
                input(editTextRokWydania, name = "rok_wydania") {
                    isNotEmpty().description("Podaj rok wydania książki !")
                    isNumber()
                    length().atMost(4).description("Rok wydania może mieć max 4 znaki")

                }
                input(editTextOpisKsiazki, name = "opis") {
                    isNotEmpty().description("Podaj Opis książki !")
                }

                spinner(spinnerAutor, name = "autor") {
                    selection().description("Wybierz Autora")
                }
                spinner(spinnerWydawnictwo, name = "telefon") {
                    selection().description("Wybierz Wydawnictwo")
                }
                spinner(spinnerCategory, name = "category") {
                    selection().description("Wybierz kategorię")
                }

                submitWith(dodajKsiazkeButton) { result ->

                    val bookData =
                        MyBooks(
                            editTextTytulKsiazki.text.toString(),
                            editTextJezykKsiazki.text.toString(),
                            editTextRokWydania.text.toString(),
                            dostepnoscCheckBox.isChecked,
                            editTextOpisKsiazki.text.toString(),
                            spinnerWydawnictwo.selectedItem as MyWydawnictwa,
                            spinnerAutor.selectedItem as MyAutor,
                            spinnerCategory.selectedItem as MyCategory
                        )
                    Log.d("NOWA KS:", "KLIKNALES PRYCISK")
                    addBook(bookData)
                }
            }
        }
        return root
    }

    fun getWydawnictwa() {
        val call = jsonPlaceholderAPI.getWydawnictwaArray()
        call!!.enqueue(object : Callback<Array<MyWydawnictwa>?> {
            override fun onResponse(
                call: Call<Array<MyWydawnictwa>?>,
                response: Response<Array<MyWydawnictwa>?>
            ) {
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    return
                }
                wydawnictwaData = response.body()!!

                spinnerWydawnictwo?.adapter = activity?.applicationContext?.let {
                    ArrayAdapter1<MyWydawnictwa?>(
                        it,
                        R.layout.support_simple_spinner_dropdown_item,
                        wydawnictwaData
                    )
                } as SpinnerAdapter
            }

            override fun onFailure(
                call: Call<Array<MyWydawnictwa>?>,
                t: Throwable
            ) {
                println(t.message)
            }
        })
    }

    fun getCategories() {
        val call = jsonPlaceholderAPI.getCategoryArray()
        call!!.enqueue(object : Callback<Array<MyCategory>?> {
            override fun onResponse(
                call: Call<Array<MyCategory>?>,
                response: Response<Array<MyCategory>?>
            ) {
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    return
                }
                categoryData = response.body()!!

                spinnerCategory?.adapter = activity?.applicationContext?.let {
                    ArrayAdapter1<MyCategory?>(
                        it,
                        R.layout.support_simple_spinner_dropdown_item,
                        categoryData
                    )
                } as SpinnerAdapter
            }

            override fun onFailure(
                call: Call<Array<MyCategory>?>,
                t: Throwable
            ) {
                println(t.message)
            }
        })
    }

    fun getAutors() {
        val call = jsonPlaceholderAPI.getAutorsArray()
        call!!.enqueue(object : Callback<Array<MyAutor>?> {
            override fun onResponse(
                call: Call<Array<MyAutor>?>,
                response: Response<Array<MyAutor>?>
            ) {
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    return
                }
                autorData = response.body()!!
                System.out.println(autorData[0].toString())
                spinnerAutor?.adapter = activity?.applicationContext?.let {
                    ArrayAdapter1<MyAutor?>(
                        it,
                        R.layout.support_simple_spinner_dropdown_item,
                        autorData
                    )
                } as SpinnerAdapter
            }

            override fun onFailure(
                call: Call<Array<MyAutor>?>,
                t: Throwable
            ) {
                println(t.message)
            }
        })
    }

    private fun addBook(MyBooks: MyBooks) {
        val call = jsonPlaceholderAPI.createBook(MyBooks)
        call.enqueue(object : Callback<MyBooks> {
            override fun onFailure(
                call: Call<MyBooks>,
                t: Throwable
            ) {
                makeToast("Brak połączenia!")
                Log.d("nowy autor:", " error")
            }

            override fun onResponse(
                call: Call<MyBooks>,
                response: Response<MyBooks>
            ) {
                if (!response.isSuccessful) {
                    makeToast("Podana ksiazka już istnieje w bazie!")
                    println("Code: " + response.code())
                    return
                } else {
                    makeToast("Dodano książkę!")
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

