package com.ware.financakotlin.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ware.financakotlin.R
import com.ware.financakotlin.extension.formatForBrazil
import com.ware.financakotlin.extension.formatForCurrencyBrazil
import com.ware.financakotlin.extension.limitString
import com.ware.financakotlin.model.Tipo
import com.ware.financakotlin.model.Transacao
import kotlinx.android.synthetic.main.transacoes_lista_recyclerview.view.*

class ListaTransacoesAdapter(
    private val transacoes: MutableList<Transacao>,
    private val context: Context
) : RecyclerView.Adapter<ListaTransacoesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.image
        val title = itemView.title
        val subtitle = itemView.subtitle
        val value = itemView.value

        fun vincula(transacao: Transacao): Unit {
            subtitle.text = transacao.data.formatForBrazil()
            value.text = transacao.valor.formatForCurrencyBrazil()
            title.text = transacao.categoria.limitString(14)

            var cor: Int = colorFor(transacao)
            value.setTextColor(cor)

            var img: Int = iconFor(transacao)
            image.setImageResource(img)
        }

        private fun iconFor(transacao: Transacao): Int {
            if (transacao.tipo == Tipo.DESPESA) {
                return R.drawable.ic_baseline_call_received_24
            }
            return R.drawable.ic_baseline_call_made_24

        }

        private fun colorFor(transacao: Transacao): Int {
            if (transacao.tipo == Tipo.DESPESA) {
                return Color.RED
            }
            return Color.GREEN
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaTransacoesAdapter.ViewHolder {
        var viewLista = LayoutInflater.from(parent.context)
            .inflate(R.layout.transacoes_lista_recyclerview, parent, false)

        return ViewHolder(viewLista);
    }

    override fun getItemCount() = transacoes.size

    override fun onBindViewHolder(holder: ListaTransacoesAdapter.ViewHolder, position: Int) {
        var transacao = transacoes[position]
        holder.vincula(transacao)
    }

}