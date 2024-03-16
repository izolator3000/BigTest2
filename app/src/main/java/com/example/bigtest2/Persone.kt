package com.example.bigtest2

import java.net.URL
import java.util.concurrent.Executors

class Persone(){
    var fullName = ""
    var address = ""
    var photo = ""
    var phone = ""
    var info = ""

    constructor(info: String) : this() {
        val personeInfo = pull_data(info)

        fullName = personeInfo[0]
        address = personeInfo[1]
        photo = personeInfo[2]
        phone = personeInfo[3]
        this.info = personeInfo[4]
    }

    fun fillData(){
        // два метода похожи. Не переделывал, потому что класс закончен и не планируется расширяться
        val personeInfo = pull_data(get_data())

        fullName = personeInfo[0]
        address = personeInfo[1]
        photo = personeInfo[2]
        phone = personeInfo[3]
        info = personeInfo[4]
    }

    fun get_data(url: String = "https://randomuser.me/api/"): String {
        /*
        Главный костыль: создание дополнительного потока, чтобы получить json без падения программы
        */
        var json = "1"
        Executors.newSingleThreadExecutor().execute({
            json =  URL(url).readText()
        })
        Thread.sleep(1000) // Пол секунды недостаточно, бывает, приложение падает
        return json
    }

    fun pull_data(data: String): Array<String> {
        // JSONObject не удалось подключить. XML библиотеки есть, а json нет
        // Re варажения тоже не сработали
        // Главное - резульатат


        // ФИО
        var new_data = data.split("title\":\"")[1]
        var prsoneName = new_data.split("\",\"first\":\"")[0] + " "
        new_data = new_data.split("\",\"first\":\"")[1]
        prsoneName += new_data.split("\",\"last\":\"")[0] + " "
        new_data = new_data.split("\",\"last\":\"")[1]
        prsoneName += new_data.split("\"},\"location")[0]

        // Адрес
        new_data = data.split("number\":")[1]
        var personeAddress = new_data.split(",\"name\":\"")[0] + " "
        new_data = new_data.split(",\"name\":\"")[1]
        personeAddress += new_data.split("\"},\"city\":\"")[0] + " "
        new_data = new_data.split("\"},\"city\":\"")[1]
        personeAddress += new_data.split("\",\"state")[0]

        // Телефон
        new_data = new_data.split("phone\":\"")[1]
        val phone = new_data.split("\",\"cell")[0]

        // Фотография
        new_data = new_data.split("large\":\"")[1]
        val photo = new_data.split("\",\"medium")[0]

        return arrayOf(prsoneName, personeAddress, photo, phone, data)
    }

    //fun printData() = println("ФИО: $fullName\nАдрес: $address\nФото: $photo\nТелефон: $phone\n\n")
}