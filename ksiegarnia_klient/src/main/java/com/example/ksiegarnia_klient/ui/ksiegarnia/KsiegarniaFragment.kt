package com.example.ksiegarnia_klient.ui.ksiegarnia

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ksiegarnia_klient.*
import kotlinx.android.synthetic.main.fragment_ksiegarnia.*
import kotlinx.android.synthetic.main.nav_header_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class KsiegarniaFragment : Fragment(), CustomBooksListAdapter.OnItemClickListener {

    private lateinit var ksiegarniaViewModel: KsiegarniaViewModel
    private lateinit var userLogin: String
    private lateinit var infoToast: Toast
    private lateinit var booksData: Array<MyBooks>
    private val baseUrl: String = "http://192.168.0.106:8080/"
    private lateinit var login: String
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit

    val thread = Executors.newSingleThreadScheduledExecutor()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ksiegarniaViewModel =
            ViewModelProviders.of(this).get(KsiegarniaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_ksiegarnia, container, false)

        //////////json
        retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)
        ////json
        loadLogin()
        makeToast("login to:" + userLogin)

        beginRefreshing()

        var swipeRefresh: SwipeRefreshLayout = root.findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            if (checkNetworkConnection()) {
                getAndShowData()
                swipeRefresh.isRefreshing = false
                makeToast("Books refreshed")
            } else {
                makeToast("Cant refresh books - no internet connection!")
            }
        }
        return root
    }

    fun getAndShowData() {
        val call = jsonPlaceholderAPI.getBookArray()
        call!!.enqueue(object : Callback<Array<MyBooks>?> {
            override fun onResponse(
                call: Call<Array<MyBooks>?>,
                response: Response<Array<MyBooks>?>
            ) {
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    return
                }
                booksData = response.body()!!
                // booksData.reverse()
                recyclerView.adapter = CustomBooksListAdapter(booksData, this@KsiegarniaFragment)
                recyclerView.layoutManager = LinearLayoutManager(context)
            }

            override fun onFailure(
                call: Call<Array<MyBooks>?>,
                t: Throwable
            ) {
                println(t.message)
            }
        })
    }

    fun checkNetworkConnection(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
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

    override fun onItemClick(//dzialanie edycji - klikniecia na cokolwiek z listy wiadomosci
        item: MyBooks, position: Int
    ) {
        val bundle = Bundle()
        bundle.putString("tytul", item.tytul)
        bundle.putString("idKsiazki", item.idKsiazki)// TODO:: ID JEST DO EDYCJI
        bundle.putString("rokWydania", item.rokWydania)
        bundle.putString("opis", item.opis)
        bundle.putString("temat", item.temat)
        bundle.putString("jezykKsiazki", item.jezykKsiazki)
        bundle.putString("rokWydania", item.rokWydania)
        bundle.putString("dostepnosc", item.dostepnosc)
        bundle.putString("autor", item.autor)
        bundle.putString("wydawnictwo", item.wydawnictwo)
        bundle.putString("dostepnosc", item.dostepnosc)

        val fragment: Fragment = BookDetailsFragment()
        fragment.arguments = bundle
        val fragmentManager: FragmentManager? = fragmentManager
        fragmentManager?.beginTransaction()
            ?.add(R.id.nav_host_fragment, fragment)//TODO::: TROCHE PRYMITYWNIE XD?
            ?.addToBackStack(this.toString())
            // ?.remove(this)
            ?.commit()
    }

    fun beginRefreshing() {
        thread.scheduleAtFixedRate({
            if (checkNetworkConnection()) {
                getAndShowData()
                Log.d("Executors thread: ", "Books refreshed automatically ")
            } else {
                Log.d(
                    "Executors thread: ",
                    "Cant automatically refresh books  - no internet connection!"
                )
            }
        }, 0, 1, TimeUnit.HOURS)// TODO:::NA RAZIE NIE DZIALA - crashuje jak zmieni sie fragment?
    }

    private fun loadLogin() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.user_login)
        userLogin =   sharedPref.getString(getString(R.string.user_login), defaultValue).toString()
    }
}