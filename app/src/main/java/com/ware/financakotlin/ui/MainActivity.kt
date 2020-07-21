package com.ware.financakotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ware.financakotlin.R
import com.ware.financakotlin.extension.formatForCurrencyBrazil
import com.ware.financakotlin.model.Tipo
import com.ware.financakotlin.ui.adapter.ListaTransacoesAdapter
import com.ware.financakotlin.model.Transacao
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cardview_resumo.*
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var generateList = generateList(17)

        configuraResumoView(generateList)
        configuraLista(generateList)

    }

    private fun configuraResumoView(generateList: ArrayList<Transacao>) {
        var decorView: View = window.decorView
        var resumoView = ResumoView(decorView, generateList)

        resumoView.adicionaDespesa()
        resumoView.adicionaReceita()
        resumoView.adicionaTotal()
    }


    private fun configuraLista(generateList: ArrayList<Transacao>) {
        lista_transacoes_reciclerview.adapter = ListaTransacoesAdapter(generateList, this)
        lista_transacoes_reciclerview.layoutManager = LinearLayoutManager(this)
        lista_transacoes_reciclerview.setHasFixedSize(true)
    }

    fun generateList(size: Int): ArrayList<Transacao>{
        val list  = ArrayList<Transacao>()
        for(i in 0 until size){
            val tipo = when(i % 3){
                0 ->  Tipo.RECEITA
                else -> Tipo.DESPESA
            }
            val item = Transacao(valor = BigDecimal(3000), categoria = "Almoço do fim de semana", tipo = tipo)
            list += item
        }
        return list
    }
}