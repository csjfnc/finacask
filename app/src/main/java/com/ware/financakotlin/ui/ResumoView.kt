package com.ware.financakotlin.ui

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.ware.financakotlin.R
import com.ware.financakotlin.extension.formatForCurrencyBrazil
import com.ware.financakotlin.model.Resumo
import com.ware.financakotlin.model.Transacao
import kotlinx.android.synthetic.main.cardview_resumo.view.*
import java.math.BigDecimal

class ResumoView(
    private val context: Context,
    private val view: View,
    transacoes: MutableList<Transacao>
) {

    private val resumo: Resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.colorReceita)
    private val corDespesa = ContextCompat.getColor(context, R.color.colorDespesa)

    fun adicionaDespesaReceitaTotal(){
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    fun adicionaDespesa() {
        var total_despeza = resumo.despeza()
        view.resumo_despesa.setTextColor(corDespesa)
        view.resumo_despesa.text = total_despeza.formatForCurrencyBrazil()
    }
    fun adicionaReceita() {
        var total_receita = resumo.receita

        with(view.resumo_receita) {
            setTextColor(corReceita)
            text = total_receita.formatForCurrencyBrazil()
        }
    }
    fun adicionaTotal() {
        var total = resumo.total
        var cor = corPor(total)
        with(view.resumo_total){
            setTextColor(cor)
            text = total.formatForCurrencyBrazil()
        }
    }

    private fun corPor(valor: BigDecimal): Int {
        if (valor >= BigDecimal.ZERO) {
            return corReceita
        }
        return corDespesa
    }
}