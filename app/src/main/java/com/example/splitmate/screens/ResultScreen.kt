package com.example.splitmate.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.splitmate.models.Calculation

@SuppressLint("DefaultLocale")
@Composable
fun ResultScreen(
    calculation: Calculation?,
    onNavigateInput: ()-> Unit,
    onNavigateHome: ()->Unit
){
    if (calculation == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Расчёт не найден")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Результаты расчёта",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Сумма счёта: ${calculation.totalAmount} ₽")
                if (calculation.tipPercent != null){
                    Text("Чаевые (${calculation.tipPercent}%): ${calculation.tipAmount} ₽")
                    Text("Итого с чаевыми: ${calculation.totalWithTip}")
                }
                else{
                    Text("Чаевые: не указаны")
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text("Количество персон: ${calculation.peopleCount}")
                Text(
                    text = "С каждого: ${String.format("%.2f",calculation.perPerson)} ₽",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onNavigateInput,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Редактировать")
            }
            Button(
                onClick = onNavigateHome,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("На главный экран")
            }
        }
    }
}