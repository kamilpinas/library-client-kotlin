package com.example.ksiegarnia_klient.ui.ksiegarnia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.ksiegarnia_klient.JsonPlaceholderAPI
import com.example.ksiegarnia_klient.R
import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BookDetailsFragment : Fragment() {

    private lateinit var bookDetailsViewModel: BookDetailsViewModel
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private val baseUrl: String = "http://192.168.0.106:8080/"
    private lateinit var wypozyczButton: Button
    private lateinit var tytulTextView: TextView
    private lateinit var autorTextView: TextView
    private lateinit var wydawnictwoTextView: TextView
    private lateinit var opisTextView: TextView
    private lateinit var rokWydania: TextView
    private lateinit var temat: TextView
    private lateinit var okladka: ImageView
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
        okladka = root.findViewById(R.id.okladkaImageView)
        rokWydania = root.findViewById(R.id.rokWydaniaTextView)
        temat = root.findViewById(R.id.tematTextView)

        wypozyczButton = root.findViewById(R.id.wypozyczButton)

        tytulTextView.text = arguments?.getString("tytul").toString()
        autorTextView.text = arguments?.getString("autor").toString()
        wydawnictwoTextView.text = arguments?.getString("wydawnictwo").toString()
        opisTextView.text = arguments?.getString("opis").toString()
        rokWydania.text = "Rok wydania: " + arguments?.getString("rokWydania").toString()
        temat.text = "Temat: " + arguments?.getString("temat").toString()
        idKsiazki = arguments?.getString("idKsiazki").toString()

        var iconUrl: String = "http:/192.168.0.106:8080/ksiegarnia/image/" + idKsiazki
        Picasso.get().load(iconUrl).into(okladka)

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

        wypozyczButton.setOnClickListener {
            Log.d("Button::", "ID KSIAZKI TO:" + idKsiazki)
        }

        return root
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val fragmentManager: FragmentManager?
            if (fragmentManager != null) {
                remove()//TODO:: CZY TO WGL DZIALA?
                fragmentManager.popBackStack()//TODO:: TO JEST NA RAZIE ZBEDNE BO w ksiegarniafragment jest   ?.addToBackStack(this.toString())
            }
        }

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
