package com.example.bigtest2

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class PersoneAdapter(var persones: List<Persone>, var context: Context):RecyclerView.Adapter<PersoneAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val photo: ImageView = view.findViewById(R.id.avarar)
        val fullName: TextView = view.findViewById(R.id.item_fullName)
        val address: TextView = view.findViewById(R.id.item_address)
        val phone: TextView = view.findViewById(R.id.item_phone)
        val btn_full_info: Button = view.findViewById(R.id.btn_fullInfo)
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

        holder.fullName.setOnClickListener {
            val email_ = persones[position].info.split("email\":\"")[1]
            val email = email_.split("\",\"login")[0]

            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                data = Uri.parse("mailto:")
                type = "text/plain"
                //putExtra(Intent.EXTRA_EMAIL, "someone") - не работает
                putExtra(Intent.EXTRA_SUBJECT, "Сделано в России")
                putExtra(Intent.EXTRA_TEXT, email)
            }
            intent.setPackage("com.google.android.gm")
            context.startActivity(intent)

            // В текст письма вставляется почта, которую надо скопировать в адрес получателя
        }

        holder.address.setOnClickListener{
            val map = "http://maps.google.co.in/maps?q=${persones[position].address}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(map))
            context.startActivity(intent)
        }

        holder.phone.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${persones[position].phone}"))
            holder.phone.getContext().startActivity(intent)
        }

        holder.photo.setImageURI(persones[position].photo.toUri())
        Glide.with(holder.photo).load(persones[position].photo).placeholder(R.drawable.ic_launcher_foreground).into(holder.photo)

        holder.btn_full_info.setOnClickListener{
            val intent = Intent(context, full_persone_info::class.java)
            intent.putExtra("info", persones[position].info)
            context.startActivity(intent)
        }
    }
}