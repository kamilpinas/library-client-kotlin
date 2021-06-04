package com.example.ksiegarnia_klient.api_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ksiegarnia_klient.R
import com.example.ksiegarnia_klient.api_data_structures.MyWypozyczenia
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_layout_wypozyczenia.view.dataWypozyczeniaTextView
import kotlinx.android.synthetic.main.list_layout_wypozyczenia.view.dataZwrotuTextView
import kotlinx.android.synthetic.main.list_layout_wypozyczenia.view.okladkaImageView
import kotlinx.android.synthetic.main.list_layout_wypozyczenia.view.tytulTextView
import kotlinx.android.synthetic.main.list_layout_wypozyczenia_klientow.view.*

class CustomWypozyczeniaKlientowListAdapter(
    private var myList: Array<MyWypozyczenia>,
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
        var imieWypozyczajacego: TextView = itemView.imieWypozyczajacegoTextView


        var okladkaImageView: ImageView = itemView.okladkaImageView

        fun initialize(item: MyWypozyczenia, action: OnItemClickListener) {
            tytulTextView.text = item.book.title
            dataWypozyczenia.text = "Data wypożyczenia: " + item.rentalDate
            dataZwrotu.text = "Data zwrotu: " + item.returnDate
            imieWypozyczajacego.text = item.client.name + " " + item.client.surname
            // nazwiskoWypozyczajacego.text=item.nazwiskoWypozyczajacego

            id = item.book.bookId.toString()
            var iconUrl: String = "http://192.168.0.4:8080/ksiegarnia/image/" + id
            Picasso.get().load(iconUrl).into(okladkaImageView)

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: MyWypozyczenia, position: Int)
    }
}
