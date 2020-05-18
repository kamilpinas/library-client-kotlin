package com.example.ppsm_budzik_shoutbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_layout.view.*

class CustomListAdapter(private var myList: Array<MyMessage>, var clickListener: OnItemClickListener) : RecyclerView.Adapter<CustomListAdapter.ExampleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate( R.layout.list_layout,
            parent, false)

        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = myList[position]
      //  holder.textContent.text = currentItem.content
       // holder.textLogin.text = currentItem.login
       // holder.textDate.text = currentItem.date.toString().substring(0, 10)
       // holder.textTime.text = currentItem.date.toString().substring(11, 19)
    holder.initialize(myList[position], clickListener)
    }

    override fun getItemCount() = myList.size

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textText: TextView = itemView.contentTextView
        var textLog: TextView = itemView.loginEditTextView
        var textDate: TextView = itemView.dateEditTextView
        var textHour: TextView = itemView.timeEditTextView
        fun initialize(item: MyMessage, action: OnItemClickListener) {
            textText.text = item.content
            textLog.text = item.login
            textDate.text = item.date.toString().subSequence(0, 10)
            textHour.text = item.date.toString().subSequence(11, 19)

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: MyMessage, position: Int)
    }
/*        class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textContent: TextView = itemView.contentTextView;
            val textLogin: TextView = itemView.loginTextView;
            val textDate: TextView = itemView.dateTextView;
            val textTime: TextView = itemView.timeTextView;
        }*/






    }