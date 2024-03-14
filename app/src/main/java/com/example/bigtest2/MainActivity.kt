package com.example.bigtest2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class MainActivity : AppCompatActivity() {



    val persons = arrayListOf<Persone>()


// Включить разрешение на File and Mdia permission
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, "Загружаем данные", Toast.LENGTH_LONG).show()

        val updateBtn = findViewById<Button>(R.id.update)
        val personeList: RecyclerView = findViewById(R.id.persone_list)

        uploadData()

        personeList.layoutManager = LinearLayoutManager(this)
        personeList.adapter = PersoneAdapter(persons, this)




        updateBtn.setOnClickListener{
            Toast.makeText(this, "Обновляем данные", Toast.LENGTH_SHORT).show()
            update_data()
            /*
            Обновить нажать
            Подождать Тоста "не сохранили"
            При ошибке нажать "подождать"
            Проскролить и данные обновлены
             */
        }
        //val full_info = Intent(this, full_persone_info::class.java)
        //startActivity(full_info) Для перехода на другой активити
    }

    fun update_data(){
        deleteData()
        uploadData()
    }

    fun deleteData(){
        for(i in 0..persons.size){
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
                val path = Environment.getExternalStorageDirectory()
                try {
                    File(path, "MyFile$i").delete()
                } catch (e: Exception) {
                    Toast.makeText(this, "Не удалили файл", Toast.LENGTH_SHORT).show()
                }
            }
        }
        persons.clear()
    }
    fun saveData(persons: List<Persone>){

        for(i in 0..persons.size){
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
                val path = Environment.getExternalStorageDirectory()

                var file = File(path, "MyFile$i")
                val buff = BufferedWriter(FileWriter(file))

                buff.write(persons[i].info)
                buff.close()
            }
        }
    }
    fun uploadData(){
        try {
            for (i in 0..10) {
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    val path = Environment.getExternalStorageDirectory()

                    val file = File(path, "MyFile$i")
                    persons.add(Persone(file.readText()))
                }
            }
        } catch (e: Exception){
            try {
                for (i in 0..10) {
                    val persone = Persone()
                    persone.fillData()
                    persons.add(persone)
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Ошибка соединения", Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(this, "Создали данные", Toast.LENGTH_SHORT).show()
            try {
                saveData(persons)
            } catch (e:Exception) {
                Toast.makeText(this, "Cохранили", Toast.LENGTH_SHORT).show()
            }
        }
    }


}