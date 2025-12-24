package com.example.splitmate.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.splitmate.models.AppState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(
    state: AppState,
    isInputValid: Boolean,
    onAmountChange: (String)->Unit,
    onPeopleCountChange: (String)-> Unit,
    onTipChange: (String)->Unit,
    onCreateNewCalculation: ()->Long,
    onNavigateResult: (Long)-> Unit,
    onNavigateBack: ()->Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text("Введите данные") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                }
            }
        )

        OutlinedTextField(
            value = state.totalAmount,
            onValueChange = { onAmountChange(it) },
            label = { Text("Общая сумма") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = state.peopleCount,
            onValueChange = { onPeopleCountChange(it) },
            label = { Text("Количество людей") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = state.tipPercent,
            onValueChange = { onTipChange(it) },
            label = { Text("Процент чаевых - по желанию :)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Button(
            onClick = {
                val calcId = onCreateNewCalculation()
                onNavigateResult(calcId)
            },
            enabled = isInputValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Рассчитать")
        }
    }
}