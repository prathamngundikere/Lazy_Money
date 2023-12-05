package com.prathamngundikere.lazymoney.ux.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTransactions(transaction: TransactionEntity)

    @Query("SELECT * FROM transaction_table ORDER BY dateTime DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Delete
    suspend fun deleteTransactions(transaction: TransactionEntity)

    @Query("SELECT SUM(amount) FROM transaction_table WHERE type = 'Income'")
    fun getIncomeTotal(): Flow<Double>

    @Query("SELECT SUM(amount) FROM transaction_table WHERE type = 'Expenditure'")
    fun getExpenditureTotal(): Flow<Double>

    @Query("SELECT * FROM transaction_table WHERE type = :transactionType ORDER BY dateTime DESC")
    fun getTransactionByType(transactionType: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transaction_table WHERE dateTime BETWEEN :startDate AND :endDate ORDER BY dateTime DESC")
    fun getTransactionsBetweenDates(startDate: Long,endDate: Long): Flow<List<TransactionEntity>>

}