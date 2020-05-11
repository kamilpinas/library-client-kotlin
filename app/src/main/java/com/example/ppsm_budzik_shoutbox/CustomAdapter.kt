package com.example.ppsm_budzik_shoutbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //PRZYPISYWANIE WARTOSCI
        val message: Message = messageList[position]
        holder?.loginTextView.text = message.login
        holder?.contentTextView.text = message.content
        holder?.dateTextView.text = message.date
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val loginTextView = itemView.findViewById(R.id.loginTextView) as TextView
        val contentTextView = itemView.findViewById(R.id.contentTextView) as TextView
        val dateTextView = itemView.findViewById(R.id.dateTextView) as TextView
    }
}