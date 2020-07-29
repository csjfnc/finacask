package com.ware.financakotlin.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.nfc.FormatException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.ware.financakotlin.R
import com.ware.financakotlin.delegate.TransacaoDelegate
import com.ware.financakotlin.extension.converteParaCalendar
import com.ware.financakotlin.extension.formatForBrazil
import com.ware.financakotlin.model.Tipo
import com.ware.financakotlin.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(private val context: Context, private val viewGroup: ViewGroup) {

    private val viewCreate = criaView()
    private val campoData = viewCreate.data_form_transacao
    private val campoCategoria = viewCreate.categoria_transacao
    private val campoValor = viewCreate.valor_transacao_form

    fun chama(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, transacaoDelegate)
    }

    private fun configuraFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {

        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCreate)
            .setPositiveButton("Salvar", DialogInterface.OnClickListener { _, _ ->

                val valorText = campoValor.text.toString()
                val datatext = campoData.text.toString()
                val categoriaText = campoCategoria.selectedItem.toString()

                val valor = converteCampoValor(valorText)
                val data = datatext.converteParaCalendar()

                val transacao = Transacao(
                    valor = valor,
                    tipo = tipo,
                    data = data,
                    categoria = categoriaText
                )
                transacaoDelegate.delegate(transacao)
            })
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }


    private fun converteCampoValor(valorText: String): BigDecimal {
        return try {
            BigDecimal(valorText)
        } catch (exception: FormatException) {
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria(tipo: Tipo) {

        val categoria = categoriaPor(tipo)

        val adapter = ArrayAdapter.createFromResource(
            context,
            categoria,
            android.R.layout.simple_spinner_dropdown_item
        )
        campoCategoria.adapter = adapter
    }

    private fun categoriaPor(tipo: Tipo): Int {
        return if (tipo == Tipo.DESPESA) {
            R.array.categoria_de_despesa
        } else {
            R.array.categoria_de_receita
        }
    }

    private fun configuraCampoData() {
        val today = Calendar.getInstance()
        val ano = today.get(Calendar.YEAR)
        val mes = today.get(Calendar.MONTH)
        val dia = today.get(Calendar.DAY_OF_MONTH)

        campoData.setText(today.formatForBrazil())
        campoData.setOnClickListener {
            DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
                val dataSelect = Calendar.getInstance()
                dataSelect.set(ano, mes, dia)
                campoData.setText(dataSelect.formatForBrazil())

            }, ano, mes, dia)
                .show()
        }
    }

    private fun criaView(): View {
        return LayoutInflater.from(context).inflate(R.layout.form_transacao, viewGroup, false)
    }
}