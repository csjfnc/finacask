package com.ware.financakotlin.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formatForBrazil() : String{
    var formatBR = "dd/MM/yyyy"
    val format = SimpleDateFormat(formatBR)
    val data = format.format(this.time)
    return data
}