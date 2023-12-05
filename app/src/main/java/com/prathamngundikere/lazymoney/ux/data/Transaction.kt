package com.prathamngundikere.lazymoney.ux.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class TransactionEntity(
    val type: String, // "Income" or "Expenditure"
    val name: String,
    val amount: Double,
    val dateTime: Long,
    val paymentMethod: String, // "Cash" or "Card"
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)
