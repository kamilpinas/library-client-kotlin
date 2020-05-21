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
        var opis: TextView = itemView.opisTextView
        var tytul: TextView = itemView.tytulTextView
        var autor: TextView = itemView.autorTextView
        var wydawnictwo: TextView = itemView.wydawnictwoTextView
        var okladka: ImageView = itemView.okladkaImageView
        var iconUrl: String="https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-1/93844876_3010798028984687_7710575402904715264_n.jpg?_nc_cat=102&_nc_sid=dbb9e7&_nc_ohc=kyouP1EEFqsAX-O3SKu&_nc_ht=scontent-waw1-1.xx&oh=e423360529fbdffbfe4549e3815cf944&oe=5EED3304"
        fun initialize(item: MyBooks, action: OnItemClickListener) {
            opis.text = item.opis
            tytul.text = item.tytul
            autor.text = item.autor
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
