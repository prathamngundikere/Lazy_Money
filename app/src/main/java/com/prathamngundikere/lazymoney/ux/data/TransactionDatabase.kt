package com.prathamngundikere.lazymoney.ux.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TransactionEntity::class],
    version = 1
)
abstract class TransactionDatabase: RoomDatabase() {
    abstract val dao: TransactionDao
}