package com.example.bigtest2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import java.net.URL
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.concurrent.Executors


class Persone(title: String, first: String, last: String, number: String, street: String, city: String, val photo: String, val phone: String, val fullData: String){
    val fullName: String
    val address: String
    init {
        fullName = "$title $first $last"
        address = "$city $street $number"
    }
    fun printData() = println("ФИО: $fullName\nАдрес: $address\nФото: $photo\nТелефон: $phone\n\n")
    fun save_fullData(path: String = "persone.txt"){
        //File(path).printWriter().use { it.write(get_data().readText())}
        try {

            val file = File(Environment.getExternalStorageDirectory(), "persone.txt")
            file.writeText(fullData)
            file.canExecute()
        }catch (e: Exception){}
    }
    fun get_data(url: String = "https://randomuser.me/api/") {

    }
}

class MainActivity : AppCompatActivity() {

    fun get_data(url: String = "https://randomuser.me/api/"): String {
        var j: String = "1"
        Executors.newSingleThreadExecutor().execute({
            val json = URL(url).readText()
            j = json
            findViewById<TextView>(R.id.bottomText).text = json

        })
        Thread.sleep(1_000)
        return j
    }

    //fun save_persone(path: String = "persone.txt") = File(path).printWriter().use {
     //   it.write(get_data().readText())}

    fun read_persone(path: String = "persone.txt") = File(path).bufferedReader().use {
        it.readText() }


    fun pull_data(data: String = get_data()): Persone {
        Toast.makeText(this, "Уже ЮРЛ", Toast.LENGTH_LONG).show()
        // ФИО
        var new_data = data.split("title\":\"")[1]
        val title = new_data.split("\",\"first\":\"")[0]
        new_data = new_data.split("\",\"first\":\"")[1]
        val first = new_data.split("\",\"last\":\"")[0]
        new_data = new_data.split("\",\"last\":\"")[1]
        val last = new_data.split("\"},\"location")[0]

        // Адрес
        new_data = data.split("number\":")[1]
        val number = new_data.split(",\"name\":\"")[0]
        new_data = new_data.split(",\"name\":\"")[1]
        val street = new_data.split("\"},\"city\":\"")[0]
        new_data = new_data.split("\"},\"city\":\"")[1]
        val city = new_data.split("\",\"state")[0]

        // Телефон
        new_data = new_data.split("phone\":\"")[1]
        val phone = new_data.split("\",\"cell")[0]

        // Фотография
        new_data = new_data.split("thumbnail\":\"")[1]
        val photo = new_data.split("\"},\"nat")[0]

        return Persone(title, first, last, number, street, city, photo, phone, data)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val fullName = findViewById<TextView>(R.id.full_name)
        val photo = findViewById<ImageView>(R.id.user_photo)
        val address = findViewById<TextView>(R.id.address)
        val phone = findViewById<TextView>(R.id.phone)
        val getDataBtn = findViewById<Button>(R.id.get_new_data)


        getDataBtn.setOnClickListener(){

            val persone = pull_data()
            //persone.save_fullData()

            fullName.text = persone.fullName
            address.text = persone.address
            photo.setImageURI(persone.photo.toUri())
            phone.text = persone.phone
        }

    }
}