package com.prathamngundikere.lazymoney.ux.presentation

import com.prathamngundikere.lazymoney.ux.data.TransactionEntity


sealed interface TransactionEvent {
    object SortTransactions: TransactionEvent
    data class DeleteTransaction(val transaction: TransactionEntity): TransactionEvent
    data class SaveTransaction(
        val type: String, // "Income" or "Expenditure"
        val name: String,
        val amount: Double,
        val paymentMethod: String // "Cash" or "Card"
    ): TransactionEvent
}