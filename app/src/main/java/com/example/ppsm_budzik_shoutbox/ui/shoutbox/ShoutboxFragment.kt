package com.example.ppsm_budzik_shoutbox.ui.shoutbox

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.lab9_f.JsonPlaceholderAPI
import com.example.ppsm_budzik_shoutbox.CustomAdapter
import com.example.ppsm_budzik_shoutbox.Message
import com.example.ppsm_budzik_shoutbox.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_shoutbox.*
import kotlinx.coroutines.NonCancellable.start
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate


class ShoutboxFragment : Fragment() {

    private lateinit var shoutboxViewModel: ShoutboxViewModel
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var infoToast: Toast
    private val baseUrl: String = "http://tgryl.pl/"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shoutboxViewModel =
            ViewModelProviders.of(this).get(ShoutboxViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_shoutbox, container, false)

        //////////json
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
        val jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI::class.java)
        //getAndShowData(jsonPlaceholderAPI)
        ////json


        var swipeRefresh: SwipeRefreshLayout = root.findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            if (checkNetworkConnection()) {
                getAndShowData(jsonPlaceholderAPI)
                swipeRefresh.isRefreshing = false
                makeToast("Odswiezono wiadomosci")
            } else {
                makeToast("Brak polaczenia z internetem")
            }
        }

        val timer = Timer("schedule", true);
        timer.scheduleAtFixedRate(0, 5000) {
            Log.d("wiadomosc:", "BLABLA")
            if (checkNetworkConnection()) {
                getAndShowData(jsonPlaceholderAPI)
               Log.d("Timer: ", "Odswiezono wiadomosci")
            } else {
                Log.d("Timer: ","Brak polaczenia z internetem")
            }
        }



        return root
    }

    fun openCloseNavigationDrawer(view: View) {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    fun getAndShowData(jsonPlaceholderAPI: JsonPlaceholderAPI) {
        val call = jsonPlaceholderAPI.getMessageArray()
        call!!.enqueue(object : Callback<Array<Message>?> {
            override fun onResponse(
                call: Call<Array<Message>?>,
                response: Response<Array<Message>?>
            ) {
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    return
                }
                val messagesData = response.body()!!
                messagesData.reverse();
                recyclerView.adapter = CustomAdapter(messagesData)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.setHasFixedSize(true)
                //LinearLayoutManager(context).apply{stackFromEnd = true}
                //LinearLayoutManager(context).apply{reverseLayout= true}
            }

            override fun onFailure(
                call: Call<Array<Message>?>,
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
}
