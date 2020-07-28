package com.ware.financakotlin.ui

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.ware.financakotlin.R
import com.ware.financakotlin.extension.formatForBrazil
import com.ware.financakotlin.extension.formatForCurrencyBrazil
import com.ware.financakotlin.model.Tipo
import com.ware.financakotlin.ui.adapter.ListaTransacoesAdapter
import com.ware.financakotlin.model.Transacao
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cardview_resumo.*
import kotlinx.android.synthetic.main.form_transacao.view.*
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

        adiciona_desposa_action.setOnClickListener {

            val view: View = window.decorView
            val ano = 0
            val mes = 0
            val dia = 0

            val viewCreate =
                LayoutInflater.from(this).inflate(R.layout.form_transacao, view as ViewGroup, false)

            val today = Calendar.getInstance()
            viewCreate.data_form_transacao.setText(today.formatForBrazil())
            viewCreate.data_form_transacao.setOnClickListener {
                DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, ano, mes, dia ->
                    val dataSelect = Calendar.getInstance()
                    dataSelect.set(ano, mes, dia)
                    viewCreate.data_form_transacao.setText(dataSelect.formatForBrazil())

                }, ano, mes, dia)
                    .show()
            }

            AlertDialog.Builder(this).setTitle(R.string.adiciona_receita).setView(viewCreate).show()


        }
    }

    private fun configuraResumoView(generateList: ArrayList<Transacao>) {
        var decorView: View = window.decorView
        var resumoView = ResumoView(this, decorView, generateList)

        resumoView.adicionaDespesaReceitaTotal()
    }


    private fun configuraLista(generateList: ArrayList<Transacao>) {
        lista_transacoes_reciclerview.adapter = ListaTransacoesAdapter(generateList, this)
        lista_transacoes_reciclerview.layoutManager = LinearLayoutManager(this)
        lista_transacoes_reciclerview.setHasFixedSize(true)
    }

    fun generateList(size: Int): ArrayList<Transacao> {
        val list = ArrayList<Transacao>()
        for (i in 0 until size) {
            val tipo = when (i % 3) {
                0 -> Tipo.RECEITA
                else -> Tipo.DESPESA
            }
            val item = Transacao(
                valor = BigDecimal(3000),
                categoria = "Almoço do fim de semana",
                tipo = tipo
            )
            list += item
        }
        return list
    }
}