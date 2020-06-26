package com.example.ksiegarnia_klient.drawer_ui.wypozyczenia

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ksiegarnia_klient.*
import com.example.ksiegarnia_klient.activities_ui.*
import com.example.ksiegarnia_klient.api_adapters.CustomWypozyczeniaKlientowListAdapter
import com.example.ksiegarnia_klient.api_adapters.CustomWypozyczeniaListAdapter
import com.example.ksiegarnia_klient.api_adapters.JsonPlaceholderAPI
import com.example.ksiegarnia_klient.api_data_structures.MyLogin
import com.example.ksiegarnia_klient.api_data_structures.MyWypozyczenia
import com.example.ksiegarnia_klient.api_data_structures.WypozyczeniaKlientow
import com.example.ksiegarnia_klient.drawer_ui.ksiegarnia.KsiegarniaFragment
import kotlinx.android.synthetic.main.fragment_ksiegarnia.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WypozyczeniaFragment : Fragment(), CustomWypozyczeniaListAdapter.OnItemClickListener,
    CustomWypozyczeniaKlientowListAdapter.OnItemClickListener {
    private lateinit var wypozyczeniaViewModel: WypozyczeniaViewModel
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit
    private lateinit var infoToast: Toast
    private lateinit var wypozyczeniaData: Array<MyWypozyczenia>
    private lateinit var wypozyczeniaKlientowData: Array<WypozyczeniaKlientow>
    private lateinit var toolbarHeaderTextView: TextView
    private lateinit var idKsiazki: Integer
    private lateinit var idKlienta: Integer


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wypozyczeniaViewModel =
            ViewModelProviders.of(this).get(WypozyczeniaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_wypozyczenia, container, false)
        retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
        jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)
        toolbarHeaderTextView = root.findViewById(R.id.toolbarHeaderTextView)

        var swipeRefresh: SwipeRefreshLayout = root.findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            if (!isAdmin && !isGuest) {//zwykly zalogowany uzytkownik
                if (checkNetworkConnection()) {
                    getAndShowWypozyczeniaKlienta()
                    swipeRefresh.isRefreshing = false
                    makeToast("Odświeżono wypozyczenia")
                } else {
                    makeToast("Nie można odświeżyć ksiązek - brak połączenia!")
                }
            } else if (isGuest) {
                makeToast("Zaloguj się jako klient, aby zobaczyć swoje wypożyczenia")
            } else {
                toolbarHeaderTextView.setText("Wypożyczenia klientów")
                if (checkNetworkConnection()) {
                    getAndShowWypozyczeniaKlientow()
                    swipeRefresh.isRefreshing = false
                    makeToast("Odświeżono wypozyczenia")
                } else {
                    makeToast("Nie można odświeżyć ksiązek - brak połączenia!")
                }
            }
        }

        if (!isAdmin && !isGuest) {//zwykly zalogowany uzytkownik
            if (checkNetworkConnection()) {
                getAndShowWypozyczeniaKlienta()
                swipeRefresh.isRefreshing = false
                makeToast("Odświeżono wypozyczenia")
            } else {
                makeToast("Nie można odświeżyć ksiązek - brak połączenia!")
            }
        } else if (isGuest) {
            makeToast("Zaloguj się jako klient, aby zobaczyć swoje wypożyczenia")
        } else {
            toolbarHeaderTextView.setText("Wypożyczenia klientów")
            if (checkNetworkConnection()) {
                getAndShowWypozyczeniaKlientow()
                swipeRefresh.isRefreshing = false
                makeToast("Kliknij na jakikolwiek element listy, aby potwierdzić otrzymanie zwrotu.")
            } else {
                makeToast("Nie można odświeżyć ksiązek - brak połączenia!")
            }
        }



        return root
    }

    fun getAndShowWypozyczeniaKlienta() {
        val call = jsonPlaceholderAPI.getWypozyczeniaArray(
            currentUserLogin,
            currentUserPassowrd
        )
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
                        this@WypozyczeniaFragment
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

    fun getAndShowWypozyczeniaKlientow() {
        val call = jsonPlaceholderAPI.getWypozyczeniaKlientowArray(
            currentUserLogin,
            currentUserPassowrd
        )
        call!!.enqueue(object : Callback<Array<WypozyczeniaKlientow>?> {
            override fun onResponse(
                call: Call<Array<WypozyczeniaKlientow>?>,
                response: Response<Array<WypozyczeniaKlientow>?>
            ) {
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    return
                }
                wypozyczeniaKlientowData = response.body()!!
                recyclerView.adapter =
                    CustomWypozyczeniaKlientowListAdapter(
                        wypozyczeniaKlientowData,
                        this@WypozyczeniaFragment
                    )
                recyclerView.layoutManager = LinearLayoutManager(context)

                recyclerView.setPadding(0, 0, 0, 340)

            }

            override fun onFailure(
                call: Call<Array<WypozyczeniaKlientow>?>,
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

    override fun onItemClick(//dzialanie edycji - klikniecia na cokolwiek z listy ksiazek
        item: WypozyczeniaKlientow, position: Int
    ) {


        idKsiazki = item.idKsiazki!!
        idKlienta = item.idKlienta!!
        showDialog()

        Log.d("id klienta", idKlienta.toString())
        Log.d("id ksiazki", idKsiazki.toString())

    }

    fun usunWypozyczenie() {
        val call =
            jsonPlaceholderAPI.usunWypozyczenie(
                currentUserLogin,
                currentUserPassowrd,
                idKsiazki,
                idKlienta
            )
        call.enqueue(object : Callback<WypozyczeniaKlientow> {
            override fun onFailure(
                call: Call<WypozyczeniaKlientow>,
                t: Throwable
            ) {
                return
            }

            override fun onResponse(
                call: Call<WypozyczeniaKlientow>,
                response: Response<WypozyczeniaKlientow>
            ) {
                if (response.isSuccessful) {
                    makeToast("Usunięto ksiązkę!")
                    val fragmentManager: FragmentManager? = fragmentManager
                    val fragment: Fragment = WypozyczeniaFragment()

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

    fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage("Czy na pewno chcesz potweirdzić zwrot książki?")
        dialogBuilder.setPositiveButton("Tak",
            DialogInterface.OnClickListener { dialog, whichButton ->
                usunWypozyczenie()
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