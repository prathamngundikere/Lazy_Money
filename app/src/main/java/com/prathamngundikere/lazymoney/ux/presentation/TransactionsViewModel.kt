package com.prathamngundikere.lazymoney.ux.presentation

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prathamngundikere.lazymoney.ux.data.TransactionDao
import com.prathamngundikere.lazymoney.ux.data.TransactionEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val dao: TransactionDao
): ViewModel() {
    private val isSortedByDateAdded = MutableStateFlow(true)
    @OptIn(ExperimentalCoroutinesApi::class)
    private var transactions =
        isSortedByDateAdded.flatMapLatest {sort->
            if (sort) {
                dao.getAllTransactions()
            } else {
                dao.getAllTransactions()
            }
        }
    val _state = MutableStateFlow(TransactionState())
    val state =
        combine(_state,isSortedByDateAdded,transactions) {state, isSortedByDateAdded, transactions ->
            state.copy(
                transactions = transactions
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TransactionState())
    fun onEvent(event: TransactionEvent){
        when (event) {
            is TransactionEvent.DeleteTransaction -> {
                viewModelScope.launch {
                    dao.deleteTransactions(event.transaction)
                }
            }
            is TransactionEvent.SaveTransaction -> {
                val transaction =TransactionEntity(
                    type = state.value.type.value,
                    name = state.value.name.value,
                    amount = state.value.amount.value,
                    paymentMethod = state.value.paymentMethod.value,
                    dateTime = System.currentTimeMillis()
                )
                viewModelScope.launch {
                    dao.upsertTransactions(transaction)
                }
                _state.update {
                    it.copy(
                        type = mutableStateOf(""), // "Income" or "Expenditure"
                        name = mutableStateOf(""),
                        amount = mutableDoubleStateOf(0.0),
                        paymentMethod = mutableStateOf("") // "Cash" or "Card"
                    )
                }
            }
            TransactionEvent.SortTransactions -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }
        }
    }
}