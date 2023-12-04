package com.prathamngundikere.lazymoney.database

import androidx.lifecycle.LiveData
import java.util.Date

class TransactionRepository(private val transactionDao: TransactionDao) {
    val allTransactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()
    suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }
    fun getIncomeTotal(): LiveData<Double> {
        return transactionDao.getIncomeTotal()
    }
    fun getExpenditureTotal(): LiveData<Double> {
        return transactionDao.getExpenditureTotal()
    }
    fun getTransactionsByType(transactionType: String): LiveData<List<Transaction>> {
        return transactionDao.getTransactionByType(transactionType)
    }
    fun getTransactionsBetweenDates(startDate: Long, endDate: Long): LiveData<List<Transaction>> {
        return transactionDao.getTransactionsBetweenDates(startDate, endDate)
    }
}