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

    fun configuraDialog(transacaoDelegate: TransacaoDelegate) {
        configuraCampoData()
        configuraCampoCategoria()
        configuraFormulario(transacaoDelegate)
    }

    private fun configuraFormulario(transacaoDelegate: TransacaoDelegate) {
        AlertDialog.Builder(context)
            .setTitle(R.string.adiciona_receita)
            .setView(viewCreate)
            .setPositiveButton("Salvar", DialogInterface.OnClickListener { _, _ ->

                val valorText = viewCreate.valor_transacao_form.text.toString()
                val datatext = viewCreate.data_form_transacao.text.toString()
                val categoriaText = viewCreate.categoria_transacao.selectedItem.toString()

                val valor = converteCampoValor(valorText)
                val data = datatext.converteParaCalendar()

                val transacao = Transacao(
                    valor = valor,
                    tipo = Tipo.RECEITA,
                    data = data,
                    categoria = categoriaText
                )
                transacaoDelegate.delegate(transacao)
            })
            .setNegativeButton("Cancelar", null)
            .show()
    }


    private fun converteCampoValor(valorText: String): BigDecimal {
        return try {
            BigDecimal(valorText)
        } catch (exception: FormatException) {
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria() {
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.categoria_de_receita,
            android.R.layout.simple_spinner_dropdown_item
        )
        viewCreate.categoria_transacao.adapter = adapter
    }

    private fun configuraCampoData() {
        val today = Calendar.getInstance()
        val ano = today.get(Calendar.YEAR)
        val mes = today.get(Calendar.MONTH)
        val dia = today.get(Calendar.DAY_OF_MONTH)

        viewCreate.data_form_transacao.setText(today.formatForBrazil())
        viewCreate.data_form_transacao.setOnClickListener {
            DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
                val dataSelect = Calendar.getInstance()
                dataSelect.set(ano, mes, dia)
                viewCreate.data_form_transacao.setText(dataSelect.formatForBrazil())

            }, ano, mes, dia)
                .show()
        }
    }

    private fun criaView(): View {
        return LayoutInflater.from(context).inflate(R.layout.form_transacao, viewGroup, false)

    }

}