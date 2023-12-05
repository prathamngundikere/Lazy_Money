package com.prathamngundikere.lazymoney.ux.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import com.prathamngundikere.lazymoney.ux.data.TransactionEntity

data class TransactionState(
    val transactions: List<TransactionEntity> = emptyList(),
    val type: MutableState<String> = mutableStateOf("Income"), // "Income" or "Expenditure"
    val name: MutableState<String> = mutableStateOf(""),
    val amount: MutableState<Double> = mutableDoubleStateOf(0.0),
    val paymentMethod: MutableState<String> = mutableStateOf("Cash") // "Cash" or "Card"
)
