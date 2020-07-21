package com.ware.financakotlin.extension

fun String.limitString(caracteres: Int): String{
    if(this.length > caracteres){
        return "${this.substring(caracteres)}..."
    }
    return this
}