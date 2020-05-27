package com.example.ksiegarnia_klient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_layout.view.*

class CustomBooksListAdapter(
    private var myList: Array<MyBooks>,
    var clickListener: OnItemClickListener
) : RecyclerView.Adapter<CustomBooksListAdapter.ExampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_layout,
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
        var tytul: TextView = itemView.tytulTextView
        var autor: TextView = itemView.autorTextView
        var wydawnictwo: TextView = itemView.wydawnictwoTextView
        var opis: TextView = itemView.opisTextView
        var okladka: ImageView = itemView.okladkaImageView
        fun initialize(item: MyBooks, action: OnItemClickListener) {
            opis.text = item.opis
            tytul.text = item.tytul
            autor.text = item.autor
            id = item.idKsiazki.toString()
            var iconUrl: String = "http:/192.168.0.106:8080/ksiegarnia/image/" + id
            wydawnictwo.text = item.wydawnictwo
            Picasso.get().load(iconUrl).into(okladka)

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: MyBooks, position: Int)
    }
}
