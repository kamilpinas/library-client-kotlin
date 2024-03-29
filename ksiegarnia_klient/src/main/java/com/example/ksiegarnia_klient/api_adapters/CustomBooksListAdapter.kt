package com.example.ksiegarnia_klient.api_adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ksiegarnia_klient.R
import com.example.ksiegarnia_klient.api_data_structures.MyBooks
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_layout_books.view.autorTextView
import kotlinx.android.synthetic.main.list_layout_books.view.dostepnoscTextView
import kotlinx.android.synthetic.main.list_layout_books.view.okladkaImageView
import kotlinx.android.synthetic.main.list_layout_books.view.opisTextView
import kotlinx.android.synthetic.main.list_layout_books.view.tytulTextView
import kotlinx.android.synthetic.main.list_layout_books.view.wydawnictwoTextView
import kotlinx.android.synthetic.main.list_layout_books.view.categoryTextView
//AAAAAAAAAAA
/**
 * Custom books list adapter
 *
 * @property myList
 * @property clickListener
 * @constructor Create empty Custom books list adapter
 */
class CustomBooksListAdapter(
    private var myList: List<MyBooks>,
    var clickListener: OnItemClickListener
) : RecyclerView.Adapter<CustomBooksListAdapter.ExampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_layout_books,
            parent, false
        )

        return ExampleViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        holder.initialize(myList[position], clickListener)
    }

    /**
     * Get item count
     *
     */
    override fun getItemCount() = myList.size

    /**
     * Example view holder
     *
     * @constructor
     *
     * @param itemView
     */

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: String = ""
        var tytulTextView: TextView = itemView.tytulTextView
        var autorTextView: TextView = itemView.autorTextView
        var dostepnoscTextView: TextView = itemView.dostepnoscTextView
        var wydawnictwoTextView: TextView = itemView.wydawnictwoTextView
        var categoryTextView: TextView = itemView.categoryTextView
        var opisTextView: TextView = itemView.opisTextView
        var okladkaImageView: ImageView = itemView.okladkaImageView


        /**
         * Initialize books
         *
         * @param item
         * @param action
         */
        fun initialize(item: MyBooks, action: OnItemClickListener) {

            if (item.availability.toString() == "true") {
                dostepnoscTextView.setTextColor(Color.parseColor("#009900"));
                dostepnoscTextView.text = "Dostępna"
            } else {
                dostepnoscTextView.setTextColor(Color.parseColor("#FF0000"));
                dostepnoscTextView.text = "Niedostępna"
            }
            opisTextView.text = item.description
            tytulTextView.text = item.title
            autorTextView.text = item.author.name.toString() + " " + item.author.surname.toString()
            categoryTextView.text = item.category.title
            id = item.bookId.toString()
            var iconUrl: String = "http://192.168.0.4:8080/library/image/" + id
            wydawnictwoTextView.text = item.publishingHouse.name.toString()
            Picasso.get().load(iconUrl).into(okladkaImageView)

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }

    /**
     * On item click listener
     *
     * @constructor Create empty On item click listener
     */
    interface OnItemClickListener {
        /**
         * On item click
         *
         * @param item
         * @param position
         */
        fun onItemClick(item: MyBooks, position: Int)
    }
}
