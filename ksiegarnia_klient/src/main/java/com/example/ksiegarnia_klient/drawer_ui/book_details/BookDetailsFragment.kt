package com.example.ksiegarnia_klient.drawer_ui.ksiegarnia

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.ksiegarnia_klient.R
import com.example.ksiegarnia_klient.activities_ui.*
import com.example.ksiegarnia_klient.api_adapters.JsonPlaceholderAPI
import com.example.ksiegarnia_klient.api_data_structures.MyLogin
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

class BookDetailsFragment : Fragment() {

    private lateinit var bookDetailsViewModel: BookDetailsViewModel
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var wypozyczButton: Button
    private lateinit var usunKsiazkeButton: Button

    private lateinit var tytulTextView: TextView
    private lateinit var autorTextView: TextView
    private lateinit var dostepnoscTextView: TextView
    private lateinit var infoToast: Toast
    private lateinit var wydawnictwoTextView: TextView
    private lateinit var opisTextView: TextView
    private lateinit var rokWydania: TextView
    private lateinit var temat: TextView
    private lateinit var okladka: ImageView
    private var idKsiazki by Delegates.notNull<Long>()
    private lateinit var marginesOpis: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookDetailsViewModel =
            ViewModelProvider(this).get(BookDetailsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_book_details, container, false)

        dostepnoscTextView = root.findViewById(R.id.dostepnoscTextView)
        tytulTextView = root.findViewById(R.id.tytulTextView)
        autorTextView = root.findViewById(R.id.autorTextView)
        wydawnictwoTextView = root.findViewById(R.id.wydawnictwoTextView)
        opisTextView = root.findViewById(R.id.opisTextView)
        okladka = root.findViewById(R.id.okladkaImageView)
        rokWydania = root.findViewById(R.id.rokWydaniaTextView)
        temat = root.findViewById(R.id.tematTextView)
        wypozyczButton = root.findViewById(R.id.wypozyczButton)
        usunKsiazkeButton = root.findViewById(R.id.usunKsiazkeButton)
        marginesOpis = root.findViewById(R.id.marginesOpis)

        if (arguments?.getBoolean("dostepnosc")!!) {
            dostepnoscTextView.setTextColor(Color.parseColor("#009900"));
            dostepnoscTextView.text = "Dostępna"
        } else {
            dostepnoscTextView.setTextColor(Color.parseColor("#FF0000"));
            dostepnoscTextView.text = "Niedostępna"
            wypozyczButton.visibility = View.GONE
        }
        tytulTextView.text = arguments?.getString("tytul").toString()
        autorTextView.text = arguments?.getSerializable("autor").toString()
        wydawnictwoTextView.text = arguments?.getSerializable("wydawnictwo").toString()
        opisTextView.text = arguments?.getString("opis").toString()
        rokWydania.text = "Rok wydania: " + arguments?.getString("rokWydania").toString()
        temat.text = "Temat: " + arguments?.getString("temat").toString()
        idKsiazki = arguments?.getLong("idKsiazki")!!



        var iconUrl: String = "http:/192.168.7.167:8080/library/image/" + idKsiazki
        Picasso.get().load(iconUrl).into(okladka)

        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)

        if (!isAdmin && !isGuest) {
            wypozyczButton.isEnabled
        } else {
            wypozyczButton.text = "Aby wypożyczyć książkę,\n zaloguj się jako klient"
            wypozyczButton.isClickable = false
            wypozyczButton.isEnabled = false
            if (isAdmin) {
                usunKsiazkeButton.visibility = View.VISIBLE
                val params = marginesOpis.getLayoutParams() as MarginLayoutParams
                params.bottomMargin = 200
            }
        }

        wypozyczButton.setOnClickListener {
            Log.d("Button::", "ID KSIAZKI TO:" + idKsiazki)
            wypozyczKsiazke()
        }

        usunKsiazkeButton.setOnClickListener {
            Log.d("Button::", "ID KSIAZKI TO:" + idKsiazki)
            usunKsiazke()
        }
        return root
    }

    fun View.setVisible(visible: Boolean) {
        visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
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

    fun wypozyczKsiazke() {
        val call =
            jsonPlaceholderAPI.wypozyczKsiazke(
                currentUserLogin,
                currentUserPassowrd, idKsiazki
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
                    makeToast("Wypozyczono książkę!")
                    val fragmentManager: FragmentManager? = fragmentManager
                    val fragment: Fragment = KsiegarniaFragment()

                    fragmentManager?.beginTransaction()
                        ?.add(
                            R.id.nav_host_fragment,
                            fragment,
                            "nazwa"
                        )
                        ?.commit()
                    return
                }
                if (!response.isSuccessful) {
                    println("ID KSAIZKI: " + idKsiazki+"login: " + currentUserLogin+"pass: " + currentUserPassowrd)
                    println("Code: " + response.code())
                    makeToast("Błąd przy wypożyczaniu!")
                    return
                }
            }
        })
    }

    fun usunKsiazke() {
        val call =
            jsonPlaceholderAPI.usunKsiazke(
                currentUserLogin,
                currentUserPassowrd, idKsiazki
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
                    makeToast("Usunięto ksiązkę!")
                    val fragmentManager: FragmentManager? = fragmentManager
                    val fragment: Fragment = KsiegarniaFragment()

                    fragmentManager?.beginTransaction()
                        ?.add(
                            R.id.nav_host_fragment,
                            fragment,
                            "nazwa"
                        )
                        ?.commit()
                    return
                }
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    makeToast("Błąd przy usuwaniu ksiazki!")
                    return
                }
            }
        })
    }

}
