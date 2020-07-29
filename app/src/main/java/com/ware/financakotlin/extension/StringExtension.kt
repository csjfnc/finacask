package com.ware.financakotlin.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitString(caracteres: Int): String{
    if(this.length > caracteres){
        return "${this.substring(caracteres)}..."
    }
    return this
}

fun String.converteParaCalendar(): Calendar {
    val formatoBrazil = SimpleDateFormat("dd/MM/yyyy")
    val dataConvertida = formatoBrazil.parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida

    return data
}