package com.example.personalfinanceapp
import com.example.personalfinanceapp.data.db.AppDatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.personalfinanceapp.ui.theme.PersonalFinanceAppTheme
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.UUID
import android.util.Log
import com.example.personalfinanceapp.data.db.entity.AccountEntity
import com.example.personalfinanceapp.data.db.entity.TransactionEntity
import com.example.personalfinanceapp.data.db.entity.TransactionType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PersonalFinanceAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        val db = AppDatabase.getInstance(this)

        lifecycleScope.launch {
            // 계좌 생성
            val account = AccountEntity(
                id = UUID.randomUUID().toString(),
                name = "Test Account",
                currency = "JPY",
                createdAt = System.currentTimeMillis()
            )

            db.accountDao().upsert(account)
            // 더미 거래 추가
            val tx = TransactionEntity(
                id = UUID.randomUUID().toString(),
                accountId = account.id,
                amount = 3000,
                type = TransactionType.EXPENSE,
                category = "Food",
                occurredAt = System.currentTimeMillis(),
                memo = "Lunch"
            )

            db.transactionDao().insert(tx)

            val list = db.accountDao().getAll()
            Log.d("DB_TEST", "accounts = $list")
        }
        runDbSmokeTest(db)
    }
    private fun runDbSmokeTest(db: AppDatabase) {
        lifecycleScope.launch {
            val account = AccountEntity(
                id = UUID.randomUUID().toString(),
                name = "Test Account",
                currency = "JPY",
                createdAt = System.currentTimeMillis()
            )
            db.accountDao().upsert(account)

            val tx = TransactionEntity(
                id = UUID.randomUUID().toString(),
                accountId = account.id,
                amount = 3000,
                type = TransactionType.EXPENSE,
                category = "Food",
                occurredAt = System.currentTimeMillis(),
                memo = "Lunch"
            )
            db.transactionDao().insert(tx)

            val balance = db.transactionDao().getBalance(account.id)
            Log.d("DB_TEST", "balance = $balance")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PersonalFinanceAppTheme {
        Greeting("Android")
    }
}