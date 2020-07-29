package com.ware.financakotlin.delegate

import com.ware.financakotlin.model.Transacao

interface TransacaoDelegate {

    fun delegate(trasacao: Transacao)
}