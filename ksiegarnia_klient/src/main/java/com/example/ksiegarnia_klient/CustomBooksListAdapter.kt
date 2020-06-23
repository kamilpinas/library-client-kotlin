package com.example.ksiegarnia_klient

import android.graphics.Color
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
        var tytulTextView: TextView = itemView.tytulTextView
        var autorTextView: TextView = itemView.autorTextView
        var dostepnoscTextView: TextView = itemView.dostepnoscTextView
        var wydawnictwoTextView: TextView = itemView.wydawnictwoTextView
        var opisTextView: TextView = itemView.opisTextView
        var okladkaImageView: ImageView = itemView.okladkaImageView
        fun initialize(item: MyBooks, action: OnItemClickListener) {
            if (item.dostepnosc.toString() == "t") {
                dostepnoscTextView.setTextColor(Color.parseColor("#009900"));
                dostepnoscTextView.text = "Dostępna"
            } else {
                dostepnoscTextView.setTextColor(Color.parseColor("#FF0000"));
                dostepnoscTextView.text = "Niedostępna"
            }
            opisTextView.text = item.opis
            tytulTextView.text = item.tytul
            autorTextView.text = item.autor
            id = item.idKsiazki.toString()
            var iconUrl: String = "http:/192.168.0.106:8080/ksiegarnia/image/" + id
            // var iconUrl: String = "http:/192.168.7.168:8080/ksiegarnia/image/" + id
            wydawnictwoTextView.text = item.wydawnictwo
            Picasso.get().load(iconUrl).into(okladkaImageView)

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: MyBooks, position: Int)
    }
}
