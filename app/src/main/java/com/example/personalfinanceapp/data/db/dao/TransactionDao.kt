package com.example.personalfinanceapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.personalfinanceapp.data.db.entity.TransactionEntity

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity)

    @Query("""
        SELECT COALESCE(SUM(
            CASE 
                WHEN type = 'INCOME' THEN amount 
                ELSE -amount 
            END
        ), 0)
        FROM transactions
        WHERE accountId = :accountId
    """)
    suspend fun getBalance(accountId: String): Long
}