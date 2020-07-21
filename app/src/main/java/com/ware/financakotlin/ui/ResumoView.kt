package com.ware.financakotlin.ui

import android.graphics.Color
import android.view.View
import com.ware.financakotlin.extension.formatForCurrencyBrazil
import com.ware.financakotlin.model.Resumo
import com.ware.financakotlin.model.Transacao
import kotlinx.android.synthetic.main.cardview_resumo.view.*
import java.math.BigDecimal

class ResumoView(private val view: View, transacoes: ArrayList<Transacao>) {

    private val resumo: Resumo = Resumo(transacoes)
    private fun corDespesa() = Color.RED
    private fun corReceita() = Color.BLUE

    fun adicionaDespesa() {
        var total_despeza = resumo.despeza()
        view.resumo_despesa.setTextColor(corDespesa())
        view.resumo_despesa.text = total_despeza.formatForCurrencyBrazil()
    }


    fun adicionaReceita() {
        var total_receita = resumo.receita()
        view.resumo_receita.setTextColor(corReceita())
        view.resumo_receita.text = total_receita.formatForCurrencyBrazil()
    }

    fun adicionaTotal(){
        var total = resumo.total()
        if(total >= BigDecimal.ZERO){
            view.resumo_total.setTextColor(corReceita())
        }else{
            view.resumo_total.setTextColor(corDespesa())
        }
        view.resumo_total.text = total.formatForCurrencyBrazil()
    }



}