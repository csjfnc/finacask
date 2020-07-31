package com.ware.financakotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ware.financakotlin.R
import com.ware.financakotlin.delegate.TransacaoDelegate
import com.ware.financakotlin.model.Tipo
import com.ware.financakotlin.ui.adapter.ListaTransacoesAdapter
import com.ware.financakotlin.model.Transacao
import com.ware.financakotlin.ui.`interface`.OnClickListener
import com.ware.financakotlin.ui.dialog.AdicionaTransacaoDialog
import com.ware.financakotlin.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.text.FieldPosition
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), OnClickListener {

    private val transacoesList: MutableList<Transacao> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configuraResumoView()
        configuraLista()
        configuraFab()
    }

    private fun configuraFab() {
        adiciona_receita_action.setOnClickListener {
            chamaDialogAdicao(Tipo.RECEITA)
        }

        adiciona_despesa_action.setOnClickListener {
            chamaDialogAdicao(Tipo.DESPESA)
        }
    }

    private fun chamaDialogAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(this, window.decorView as ViewGroup)
            .chama(tipo, object : TransacaoDelegate {
                override fun delegate(trasacao: Transacao) {
                    transacoesList.add(trasacao)
                    atualizaTransacoes()
                    floaction_menu_transacao.close(true)
                }
            })
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumoView()
    }

    private fun configuraResumoView() {
        var decorView: View = window.decorView
        var resumoView = ResumoView(this, decorView, transacoesList)
        resumoView.adicionaDespesaReceitaTotal()
    }


    private fun configuraLista() {
        lista_transacoes_reciclerview.adapter = ListaTransacoesAdapter(transacoesList, this, this)
        lista_transacoes_reciclerview.layoutManager = LinearLayoutManager(this)
        lista_transacoes_reciclerview.setHasFixedSize(true)
    }

    override fun onClickView(transacao: Transacao, position: Int) {
        AlteraTransacaoDialog(this, window.decorView as ViewGroup)
            .chama(transacao, object : TransacaoDelegate{
                override fun delegate(trasacao: Transacao) {
                    transacoesList[position] = trasacao
                    atualizaTransacoes()
                }

            })
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