package com.ware.financakotlin.ui

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.nfc.FormatException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.ware.financakotlin.R
import com.ware.financakotlin.delegate.TransacaoDelegate
import com.ware.financakotlin.extension.formatForBrazil
import com.ware.financakotlin.extension.formatForCurrencyBrazil
import com.ware.financakotlin.model.Tipo
import com.ware.financakotlin.ui.adapter.ListaTransacoesAdapter
import com.ware.financakotlin.model.Transacao
import com.ware.financakotlin.ui.dialog.AdicionaTransacaoDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cardview_resumo.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val transacoesList: MutableList<Transacao> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var generateList = generateList(17)

        configuraResumoView()
        configuraLista()

        adiciona_desposa_action.setOnClickListener {

            AdicionaTransacaoDialog(this, window.decorView as ViewGroup)
                .configuraDialog(object : TransacaoDelegate {
                    override fun delegate(trasacao: Transacao) {
                        atualizaTransacoes(trasacao)
                        floaction_menu_transacao.close(true)
                    }
                })
        }
    }

    private fun atualizaTransacoes(transacao: Transacao) {
        transacoesList.add(transacao)
        configuraLista()
        configuraResumoView()
    }

    private fun configuraResumoView() {
        var decorView: View = window.decorView
        var resumoView = ResumoView(this, decorView, transacoesList)
        resumoView.adicionaDespesaReceitaTotal()
    }


    private fun configuraLista() {
        lista_transacoes_reciclerview.adapter = ListaTransacoesAdapter(transacoesList, this)
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