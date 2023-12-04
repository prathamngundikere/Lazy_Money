package com.prathamngundikere.lazymoney.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transaction_table")
data class Transaction(
    val type: String, // "Income" or "Expenditure"
    val name: String,
    val amount: Double,
    val dateTime: Long,
    val paymentMethod: String // "Cash" or "Card"
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
