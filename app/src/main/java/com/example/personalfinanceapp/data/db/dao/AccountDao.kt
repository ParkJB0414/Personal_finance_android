package com.example.personalfinanceapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.personalfinanceapp.data.db.entity.AccountEntity

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(account: AccountEntity)

    @Query("SELECT * FROM account ORDER BY createdAt DESC")
    suspend fun getAll(): List<AccountEntity>
}
