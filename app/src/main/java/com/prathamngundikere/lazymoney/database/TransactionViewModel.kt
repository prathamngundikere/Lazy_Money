package com.prathamngundikere.lazymoney.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class TransactionViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TransactionRepository
    val allTransactions: LiveData<List<Transaction>>

    init {
        val transactionDao = AppDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(transactionDao)
        allTransactions = repository.allTransactions
    }

    fun insert(transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(transaction)
    }

    fun getIncomeTotal(): LiveData<Double> {
        return repository.getIncomeTotal()
    }

    fun getExpenditureTotal(): LiveData<Double> {
        return repository.getExpenditureTotal()
    }

    fun getTransactionsByType(transactionType: String): LiveData<List<Transaction>> {
        return repository.getTransactionsByType(transactionType)
    }

    fun getTransactionsBetweenDates(startDate: Long,endDate: Long): LiveData<List<Transaction>> {
        return repository.getTransactionsBetweenDates(startDate, endDate)
    }
}