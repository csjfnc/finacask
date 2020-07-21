package com.ware.financakotlin.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Locale

fun BigDecimal.formatForCurrencyBrazil() : String{
    var currencyFormatBrazil = DecimalFormat.getCurrencyInstance(Locale("pt", "br"))
    return currencyFormatBrazil.format(this).replace("-R$", "R$ -")
}