package com.prathamngundikere.lazymoney.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Upsert
    suspend fun upsertTransaction(transaction: Transaction)
    @Delete
    suspend fun deleteTransaction(transaction: Transaction)
    @Query("SELECT * FROM 'transaction' ORDER BY dateAdded")
    fun getTransactionOrderedByDateAdded(): Flow<List<Transaction>>
}