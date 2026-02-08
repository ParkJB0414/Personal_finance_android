package com.example.personalfinanceapp.data.db

import androidx.room.TypeConverter
import com.example.personalfinanceapp.data.db.entity.TransactionType

class Converters {

    @TypeConverter
    fun fromTransactionType(value: TransactionType): String {
        return value.name
    }

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }
}
