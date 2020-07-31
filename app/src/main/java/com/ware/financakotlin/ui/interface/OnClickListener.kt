package com.ware.financakotlin.ui.`interface`

import com.ware.financakotlin.model.Transacao
import java.text.FieldPosition

interface OnClickListener {

    fun onClickView(transacao: Transacao, position: Int)
}