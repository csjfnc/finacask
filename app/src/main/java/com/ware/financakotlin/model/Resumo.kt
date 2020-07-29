package com.ware.financakotlin.model

import java.math.BigDecimal

class Resumo(private val transacoes: MutableList<Transacao>){

    fun despeza(): BigDecimal {
//        var total_despesa = BigDecimal.ZERO
//        for (transacaoes in transacoes) {
//            if (transacaoes.tipo == Tipo.DESPESA) {
//                total_despesa = total_despesa.plus(transacaoes.valor)
//            }
//        }
        return somaPor(Tipo.DESPESA)
    }

    val receita get() = somaPor(Tipo.RECEITA)
    val total get() = receita.subtract(despeza())

    fun somaPor(tipo: Tipo): BigDecimal = BigDecimal(transacoes.filter { it.tipo == tipo }
        .sumByDouble { it.valor.toDouble() })
}