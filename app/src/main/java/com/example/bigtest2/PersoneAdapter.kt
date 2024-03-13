package com.example.bigtest2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersoneAdapter(var persones: List<Persone>, var context: Context):RecyclerView.Adapter<PersoneAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val photo: ImageView = view.findViewById(R.id.avarar)
        val fullName: TextView = view.findViewById(R.id.item_fullName)
        val address: TextView = view.findViewById(R.id.item_address)
        val phone: TextView = view.findViewById(R.id.item_phone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.persone_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return persones.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.fullName.text = persones[position].fullName
        holder.address.text = persones[position].address
        holder.phone.text = persones[position].phone
    }


}