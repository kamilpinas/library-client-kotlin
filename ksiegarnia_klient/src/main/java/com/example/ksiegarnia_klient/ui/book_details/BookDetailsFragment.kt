package com.example.ksiegarnia_klient.ui.ksiegarnia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.ksiegarnia_klient.MyMessage
import com.example.ksiegarnia_klient.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.ksiegarnia_klient.JsonPlaceholderAPI
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_layout.*

class BookDetailsFragment : Fragment() {

    private lateinit var bookDetailsViewModel: BookDetailsViewModel
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private val baseUrl: String = "http://192.168.0.106:8080/"
    private lateinit var button: Button
    private lateinit var tytulTextView: TextView
    private lateinit var autorTextView: TextView
    private lateinit var wydawnictwoTextView: TextView
    private lateinit var opisTextView: TextView
    private lateinit var idKsiazkiTextView: TextView
    private lateinit var okladka: ImageView

    private lateinit var tytul: String
    private lateinit var autor: String
    private lateinit var wydawnictwo: String
    private lateinit var opis: String
    private lateinit var idKsiazki: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookDetailsViewModel =
            ViewModelProvider(this).get(BookDetailsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_book_details, container, false)

        tytulTextView = root.findViewById(R.id.tytulTextView)
        autorTextView = root.findViewById(R.id.autorTextView)
        wydawnictwoTextView = root.findViewById(R.id.wydawnictwoTextView)
        opisTextView = root.findViewById(R.id.opisTextView)
        okladka= root.findViewById(R.id.okladkaImageView)

        button = root.findViewById(R.id.wypozyczButton)

        tytul = arguments?.getString("tytul").toString()
        autor = arguments?.getString("autor").toString()
        wydawnictwo = arguments?.getString("wydawnictwo").toString()
        opis = arguments?.getString("opis").toString()

        tytulTextView.text = tytul
        autorTextView.text = autor
        wydawnictwoTextView.text = wydawnictwo
        opisTextView.text = opis


        //////////json
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)
        //json

        /*  button.setOnClickListener {
              //dzialanie przycisku save edit
              content = editTextContent.text.toString()
              putData()
              val bundle = Bundle()
            //  bundle.putString("login", login)
              val fragment: Fragment = KsiegarniaFragment()
              fragment.arguments = bundle
              val fragmentManager: FragmentManager? = fragmentManager
              fragmentManager?.beginTransaction()
                  ?.replace(R.id.nav_host_fragment, fragment)
                  ?.commit()
          }*/

        return root
    }

    /*private fun putData() {
        val message = MyMessage(login, content)

        val call = jsonPlaceholderAPI.createPut(id, message)

        call.enqueue(object : Callback<MyMessage> {
            override fun onResponse(
                call: Call<MyMessage>,
                response: Response<MyMessage>
            ) {
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    return
                }
            }

            override fun onFailure(
                call: Call<MyMessage>,
                t: Throwable
            ) {
                println(t.message)
            }
        })
    }*/
}
