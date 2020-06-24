package com.example.ksiegarnia_klient.ui.wypozyczenia_klienta

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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ksiegarnia_klient.*
import com.example.ksiegarnia_klient.api_adapters.CustomWypozyczeniaListAdapter
import com.example.ksiegarnia_klient.api_adapters.JsonPlaceholderAPI
import com.example.ksiegarnia_klient.api_data_structures.MyWypozyczenia
import kotlinx.android.synthetic.main.fragment_ksiegarnia.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WypozyczeniaKlientaFragment : Fragment(), CustomWypozyczeniaListAdapter.OnItemClickListener {
    private lateinit var wypozyczeniaKlientaViewModel: WypozyczeniaKlientaViewModel
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit
    private lateinit var infoToast: Toast
    private lateinit var wypozyczeniaData: Array<MyWypozyczenia>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wypozyczeniaKlientaViewModel =
            ViewModelProviders.of(this).get(WypozyczeniaKlientaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_wypozyczenia_klienta, container, false)
        retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)
        if (!isAdmin && !isGuest) {

            if (checkNetworkConnection()) {
                getAndShowData()
                makeToast("Odświeżono książki")
            } else {
                makeToast("Nie można odświeżyć ksiązek - brak połączenia!")
            }
        } else {
            Log.d("POBIERANIE WYPOZXYCZEN", "JESTES GOSCIEM LUB ADMINEM")
            makeToast("Zaloguj się jako klient, aby zobaczyć swoje wypożyczenia")
        }


        var swipeRefresh: SwipeRefreshLayout = root.findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            if (checkNetworkConnection()) {
                getAndShowData()
                swipeRefresh.isRefreshing = false
                makeToast("Odświeżono wypożyczenia")
            } else {
                makeToast("Nie można odświeżyć wypożyczeń - brak połączenia!")
            }
        }
        return root
    }

    fun getAndShowData() {
        val call = jsonPlaceholderAPI.getWypozyczeniaArray(currentUserLogin, currentUserPassowrd)
        call!!.enqueue(object : Callback<Array<MyWypozyczenia>?> {
            override fun onResponse(
                call: Call<Array<MyWypozyczenia>?>,
                response: Response<Array<MyWypozyczenia>?>
            ) {
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    return
                }
                wypozyczeniaData = response.body()!!
                recyclerView.adapter =
                    CustomWypozyczeniaListAdapter(
                        wypozyczeniaData,
                        this@WypozyczeniaKlientaFragment
                    )
                recyclerView.layoutManager = LinearLayoutManager(context)
            }

            override fun onFailure(
                call: Call<Array<MyWypozyczenia>?>,
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
        item: MyWypozyczenia, position: Int
    ) {
    }
}