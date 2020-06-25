package com.example.ksiegarnia_klient.drawer_ui.ksiegarnia

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
import com.example.ksiegarnia_klient.activities_ui.baseUrl
import com.example.ksiegarnia_klient.activities_ui.isAdmin
import com.example.ksiegarnia_klient.api_adapters.CustomBooksListAdapter
import com.example.ksiegarnia_klient.api_adapters.JsonPlaceholderAPI
import com.example.ksiegarnia_klient.api_data_structures.MyBooks
import kotlinx.android.synthetic.main.fragment_ksiegarnia.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class KsiegarniaFragment : Fragment(), CustomBooksListAdapter.OnItemClickListener {

    private lateinit var ksiegarniaViewModel: KsiegarniaViewModel
    private lateinit var userLogin: String
    private lateinit var booksData: Array<MyBooks>
    private lateinit var login: String
    private lateinit var pass: String
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit
    private lateinit var infoToast: Toast

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

        if (checkNetworkConnection()) {
            getAndShowData()
            makeToast("Odświeżono książki")
        } else {
            makeToast("Nie można odświeżyć ksiązek - brak połączenia!")
        }

        var swipeRefresh: SwipeRefreshLayout = root.findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            if (checkNetworkConnection()) {
                getAndShowData()
                swipeRefresh.isRefreshing = false
                makeToast("Odświeżono książki")
            } else {
                makeToast("Nie można odświeżyć ksiązek - brak połączenia!")
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
                recyclerView.adapter =
                    CustomBooksListAdapter(
                        booksData,
                        this@KsiegarniaFragment
                    )
                recyclerView.layoutManager = LinearLayoutManager(context)
                if (isAdmin) {
                    recyclerView.setPadding(0, 0, 0, 340)
                }

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

    override fun onItemClick(//dzialanie edycji - klikniecia na cokolwiek z listy ksiazek
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
            ?.add(R.id.nav_host_fragment, fragment, "nazwa")
            ?.addToBackStack(this.toString())
            // ?.remove(this)

            ?.commit()
    }

    /*
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
    }*/

    private fun loadLogin() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = "default_login"
        login = sharedPref.getString("user_login", defaultValue).toString()
        pass = sharedPref.getString("user_password", defaultValue).toString()
    }
}