package com.example.personalfinanceapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val accountId: String,
    val amount: Long,
    val type: TransactionType,
    val category: String,
    val occurredAt: Long,
    val memo: String?
)
