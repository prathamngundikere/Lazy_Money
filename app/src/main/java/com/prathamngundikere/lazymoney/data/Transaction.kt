package com.prathamngundikere.lazymoney.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    val title: String,
    val amount: Float,
    val dateAdded: Long,
    val transactionType: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
