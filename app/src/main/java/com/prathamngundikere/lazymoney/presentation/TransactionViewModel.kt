package com.prathamngundikere.lazymoney.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prathamngundikere.lazymoney.data.Transaction
import com.prathamngundikere.lazymoney.data.TransactionDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val dao: TransactionDao
): ViewModel() {
    private val transactions =
        dao.getTransactionOrderedByDateAdded()
            .stateIn(viewModelScope,SharingStarted.WhileSubscribed(),emptyList())
    val _state = MutableStateFlow(TransactionState())
    val state =
        combine(_state, transactions) { state, transactions ->
            state.copy(
                transactions = transactions
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),TransactionState())
    fun onEvent(event: TransactionEvent) {
        when(event){
            is TransactionEvent.DeleteTransaction -> {
                viewModelScope.launch {
                    dao.deleteTransaction(event.transaction)
                }
            }
            is TransactionEvent.SaveTransaction -> {
                val transaction = Transaction(
                    title = state.value.title.value,
                    amount = state.value.amount.value,
                    transactionType = state.value.transactionType.value,
                    dateAdded = System.currentTimeMillis()
                )
            }
            TransactionEvent.SortTransactions -> {
                transactions
            }
        }
    }
}