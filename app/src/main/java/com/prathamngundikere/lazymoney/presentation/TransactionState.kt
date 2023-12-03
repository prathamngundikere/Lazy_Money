package com.prathamngundikere.lazymoney.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.prathamngundikere.lazymoney.data.Transaction

data class TransactionState(
    val transactions: List<Transaction> = emptyList(),
    val title: MutableState<String> = mutableStateOf(""),
    val amount: MutableState<Float> = mutableFloatStateOf(0.0f),
    val transactionType: MutableState<Int> = mutableIntStateOf(0)
)
