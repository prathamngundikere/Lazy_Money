package com.prathamngundikere.lazymoney.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.Date

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM transaction_table ORDER BY dateTime DESC")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("SELECT SUM(amount) FROM transaction_table WHERE type = 'Income'")
    fun getIncomeTotal(): LiveData<Double>

    @Query("SELECT SUM(amount) FROM transaction_table WHERE type = 'Expenditure'")
    fun getExpenditureTotal(): LiveData<Double>

    @Query("SELECT * FROM transaction_table WHERE type = :transactionType ORDER BY dateTime DESC")
    fun getTransactionByType(transactionType: String): LiveData<List<Transaction>>

    @Query("SELECT * FROM transaction_table WHERE dateTime BETWEEN :startDate AND :endDate ORDER BY dateTime DESC")
    fun getTransactionsBetweenDates(startDate: Long,endDate: Long): LiveData<List<Transaction>>

}