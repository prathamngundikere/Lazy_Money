package com.prathamngundikere.lazymoney.presentation

import com.prathamngundikere.lazymoney.data.Transaction

sealed interface TransactionEvent {
    object SortTransactions: TransactionEvent
    data class DeleteTransaction(val transaction: Transaction): TransactionEvent
    data class SaveTransaction(
        val title: String,
        val amount: Float,
        val transactionType: Int
    ): TransactionEvent
}