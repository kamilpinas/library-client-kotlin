package com.example.ksiegarnia_klient.drawer_ui.ksiegarnia

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.widget.doAfterTextChanged
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
import com.example.ksiegarnia_klient.api_data_structures.MyCategory
import kotlinx.android.synthetic.main.fragment_ksiegarnia.*
import kotlinx.android.synthetic.main.fragment_ksiegarnia.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Bookstore fragment - Screen with all books sorted by availability first
 *
 * @constructor Create empty Bookstore fragment
 */
class KsiegarniaFragment : Fragment(), CustomBooksListAdapter.OnItemClickListener {

    private lateinit var ksiegarniaViewModel: KsiegarniaViewModel
    private lateinit var userLogin: String
    private lateinit var booksData: Array<MyBooks>
    private lateinit var categoryData: Array<MyCategory>
    private lateinit var login: String
    private lateinit var pass: String
    private lateinit var jsonPlaceholderAPI: JsonPlaceholderAPI
    private lateinit var retrofit: Retrofit
    private lateinit var infoToast: Toast
    private lateinit var spinnerCategory: Spinner

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ksiegarniaViewModel =
            ViewModelProviders.of(this).get(KsiegarniaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_ksiegarnia, container, false)
        spinnerCategory = root.findViewById(R.id.spinnerCategory)
        spinnerCategory.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            @Override
            override fun onItemSelected(
                adapter: AdapterView<*>,
                v: View,
                i: Int,
                lng: Long
            ) {
                if (spinnerCategory.selectedItemId.toInt() == 0) {
                    getAndShowData()
                } else {
                    getBooksByCategory(spinnerCategory.selectedItemId);
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        })
        var searchBarText: TextView = root.searchBarText

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
            getCategories()
            makeToast("Odświeżono książki")
        } else {
            makeToast("Nie można odświeżyć ksiązek - brak połączenia!")
        }

        var swipeRefresh: SwipeRefreshLayout = root.findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            if (checkNetworkConnection()) {
                getAndShowData()
                getCategories()
                swipeRefresh.isRefreshing = false
                makeToast("Odświeżono książki")
            } else {
                makeToast("Nie można odświeżyć ksiązek - brak połączenia!")
            }
        }

        searchBarText.doAfterTextChanged { getAndShowData() }


        return root
    }

    /**
     * Get and show all books
     *
     */
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
                println(response.body())
                booksData.reverse()
                var filteredBooksList: List<MyBooks> = booksData.toList();
                if (searchBarText.text.toString() != "" && spinnerCategory.selectedItemId.toInt() == 0) {
                    filteredBooksList =
                        filteredBooksList.filter {
                            it.title!!.toLowerCase()!!.contains(searchBarText.text.toString())
                        }
                } else {
                    if (searchBarText.text.toString() != "" && spinnerCategory.selectedItemId.toInt() != 0) {
                        filteredBooksList =
                            filteredBooksList.filter {
                                it.title!!.toLowerCase()!!
                                    .contains(searchBarText.text.toString()) &&
                                        it.category.categoryId == (spinnerCategory.selectedItemId)
                            }
                    }
                }
                if (searchBarText.toString() == "") {
                    getAndShowData()
                }
                recyclerView.adapter =
                    CustomBooksListAdapter(
                        filteredBooksList,
                        this@KsiegarniaFragment
                    )
                recyclerView.layoutManager = LinearLayoutManager(context)
                if (isAdmin) {
                    recyclerView.setPadding(0, 0, 0, 170)
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


    fun getBooksByCategory(categoryId: Long) {
        val call = jsonPlaceholderAPI.getBooksByCategoryAPI(categoryId)
        if (call != null) {
            System.out.println(call.request().url().toString());
        }
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
                booksData.reverse()
                booksData.toList();
                var filteredBooksList: List<MyBooks> = booksData.toList();

                recyclerView.adapter =
                    CustomBooksListAdapter(
                        filteredBooksList,
                        this@KsiegarniaFragment
                    )
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
                var categoryDataList: MutableList<MyCategory> = categoryData.toMutableList();
                categoryDataList.add(0, MyCategory("wszystkie"));
                categoryData = categoryDataList.toTypedArray();

                spinnerCategory.adapter = activity?.applicationContext?.let {
                    ArrayAdapter<MyCategory?>(
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

    /**
     * Check network connection
     *
     * @return
     */
    @RequiresApi(Build.VERSION_CODES.M)
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

    /**
     * Make toast
     *
     * @param myToastText
     */
    fun makeToast(myToastText: String) {
        infoToast = Toast.makeText(
            context,
            myToastText,
            Toast.LENGTH_SHORT
        )
        infoToast.setGravity(Gravity.TOP, 0, 200)
        infoToast.show()
    }

    /**
     * On item click open book details screen
     *
     * @param item
     * @param position
     */
    override fun onItemClick(//dzialanie edycji - klikniecia na cokolwiek z listy ksiazek
        item: MyBooks, position: Int
    ) {
        val bundle = Bundle()
        bundle.putString("tytul", item.title)
        bundle.putLong("idKsiazki", item.bookId!!)// TODO:: ID JEST DO EDYCJI
        bundle.putString("rokWydania", item.publicationDate)
        bundle.putString("opis", item.description)
        bundle.putString("temat", item.category.title)
        bundle.putString("jezykKsiazki", item.bookLanguage)
        bundle.putString("rokWydania", item.publicationDate)
        bundle.putString("dostepnosc", item.availability.toString())
        bundle.putString("autor", item.author.name + " " + item.author.surname)
        bundle.putString("wydawnictwo", item.publishingHouse.name)

        val fragment: Fragment = BookDetailsFragment()
        fragment.arguments = bundle

        val fragmentManager: FragmentManager? = fragmentManager
        fragmentManager?.beginTransaction()
            ?.add(R.id.nav_host_fragment, fragment, "nazwa")
            ?.addToBackStack(this.toString())
            // ?.remove(this)

            ?.commit()
    }

    private fun loadLogin() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = "default_login"
        login = sharedPref.getString("user_login", defaultValue).toString()
        pass = sharedPref.getString("user_password", defaultValue).toString()
    }
}