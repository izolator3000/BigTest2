package com.example.bigtest2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val personeList: RecyclerView = findViewById(R.id.persone_list)
        val persons = arrayListOf<Persone>()

        for (i in 1..10) {
            var persone = Persone()
            persone.fillData()
            persons.add(persone)
        }

        personeList.layoutManager = LinearLayoutManager(this)
        personeList.adapter = PersoneAdapter(persons, this)


        //val full_info = Intent(this, full_persone_info::class.java)
        //startActivity(full_info) Для перехода на другой активити


        //getDataBtn.setOnClickListener(){

            //val persone = pull_data(get_data())

            /*fullName.text = persone.fullName
            address.text = persone.address
            photo.setImageURI(persone.photo.toUri())
            Glide.with(applicationContext).load(persone.photo).placeholder(R.drawable.ic_launcher_foreground).into(photo)
            phone.text = persone.phone*/
        //}

    }
}