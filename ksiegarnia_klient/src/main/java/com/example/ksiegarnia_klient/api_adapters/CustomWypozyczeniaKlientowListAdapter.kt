package com.example.ksiegarnia_klient.api_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ksiegarnia_klient.R
import com.example.ksiegarnia_klient.api_data_structures.MyWypozyczenia
import com.example.ksiegarnia_klient.api_data_structures.WypozyczeniaKlientow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_layout_wypozyczenia.view.*
import kotlinx.android.synthetic.main.list_layout_wypozyczenia.view.dataWypozyczeniaTextView
import kotlinx.android.synthetic.main.list_layout_wypozyczenia.view.dataZwrotuTextView
import kotlinx.android.synthetic.main.list_layout_wypozyczenia.view.okladkaImageView
import kotlinx.android.synthetic.main.list_layout_wypozyczenia.view.tytulTextView
import kotlinx.android.synthetic.main.list_layout_wypozyczenia_klientow.view.*

class CustomWypozyczeniaKlientowListAdapter(
    private var myList: Array<WypozyczeniaKlientow>,
    var clickListener: OnItemClickListener
) : RecyclerView.Adapter<CustomWypozyczeniaKlientowListAdapter.ExampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_layout_wypozyczenia_klientow,
            parent, false
        )

        return ExampleViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        holder.initialize(myList[position], clickListener)
    }

    override fun getItemCount() = myList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: String = ""
        var tytulTextView: TextView = itemView.tytulTextView
        var dataWypozyczenia: TextView = itemView.dataWypozyczeniaTextView
        var dataZwrotu: TextView = itemView.dataZwrotuTextView
        var imieWypozyczajacego:TextView = itemView.imieWypozyczajacegoTextView
        var nazwiskoWypozyczajacego:TextView = itemView.nazwiskoWypozyczajacegoTextView
        var  potwierdzZwrotButton: Button =itemView.potwierdzZwrotButton


        var okladkaImageView: ImageView = itemView.okladkaImageView

        fun initialize(item: WypozyczeniaKlientow, action: OnItemClickListener) {
            tytulTextView.text = item.tytulKsiazki
            dataWypozyczenia.text="Data wypożyczenia: "+ item.dataWypozyczenia
            dataZwrotu.text="Data zwrotu: "+ item.dataZwrotu
            imieWypozyczajacego.text=item.imieWypozyczajacego+" "+ item.nazwiskoWypozyczajacego
           // nazwiskoWypozyczajacego.text=item.nazwiskoWypozyczajacego

            id = item.idKsiazki.toString()
            var iconUrl: String = "http:/192.168.0.106:8080/ksiegarnia/image/" + id
            // var iconUrl: String = "http:/192.168.7.168:8080/ksiegarnia/image/" + id
            Picasso.get().load(iconUrl).into(okladkaImageView)

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: WypozyczeniaKlientow, position: Int)
    }
}
