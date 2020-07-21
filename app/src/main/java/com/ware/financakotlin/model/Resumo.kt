package com.ware.financakotlin.model

import java.math.BigDecimal

class Resumo(private val transacoes: ArrayList<Transacao>){

    fun despeza(): BigDecimal {
        var total_despesa = BigDecimal.ZERO
        for (transacaoes in transacoes  ) {
            if (transacaoes.tipo == Tipo.DESPESA) {
                total_despesa = total_despesa.plus(transacaoes.valor)
            }
        }
        return total_despesa
    }

    fun receita():BigDecimal{
        var total_receita = BigDecimal.ZERO
        for (transacaoes in transacoes) {
            if (transacaoes.tipo == Tipo.RECEITA) {
                total_receita = total_receita.plus(transacaoes.valor)
            }
        }
        return total_receita
    }

    fun total() : BigDecimal{
        return receita().subtract(despeza())
    }
}