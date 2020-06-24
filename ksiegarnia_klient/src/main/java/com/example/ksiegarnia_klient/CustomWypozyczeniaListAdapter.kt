package com.example.ksiegarnia_klient

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_layout_wypozyczenia.view.*

class CustomWypozyczeniaListAdapter(
    private var myList: Array<MyWypozyczenia>,
    var clickListener: OnItemClickListener
) : RecyclerView.Adapter<CustomWypozyczeniaListAdapter.ExampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_layout_wypozyczenia,
            parent, false
        )

        return ExampleViewHolder(itemView)
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
        var okladkaImageView: ImageView = itemView.okladkaImageView

        fun initialize(item: MyWypozyczenia, action: OnItemClickListener) {
            tytulTextView.text = "Tytuł: " +item.tytulKsiazki
            dataWypozyczenia.text="Data wypożyczenia: "+ item.dataWypozyczenia
            dataZwrotu.text="Data zwrotu: "+ item.dataZwrotu
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
        fun onItemClick(item: MyWypozyczenia, position: Int)
    }
}
