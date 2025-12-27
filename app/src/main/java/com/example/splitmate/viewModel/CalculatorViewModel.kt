package com.example.splitmate.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.splitmate.models.AppState
import com.example.splitmate.models.Calculation


class SplitViewModel : ViewModel(){
    var uiState by mutableStateOf(AppState())
        private set

    fun updateTotalAmount(amount: String){
        uiState = uiState.copy(totalAmount = amount)
    }

    fun updatePeopleCount(count: String){
        uiState = uiState.copy(peopleCount = count)
    }

    fun updateTipPercent(tip: String){
        uiState = uiState.copy(tipPercent = tip)
    }

    fun createNewCalculation(): Long {
        val total = uiState.totalAmount.toDoubleOrNull() ?: 0.0
        val people = uiState.peopleCount.toIntOrNull() ?: 1
        val tip = uiState.tipPercent.takeIf { it.isNotBlank() }?.toDoubleOrNull()

        val newCalculation = Calculation(
            totalAmount = total,
            peopleCount = people,
            tipPercent = tip
        )

        val updatedHistory = listOf(newCalculation) + uiState.history.take(4)
        uiState = uiState.copy(
            currentCalculation = newCalculation,
            history = updatedHistory
        )
        return newCalculation.id
    }

    fun getCalculationById(id : Long): Calculation? {
        return uiState.history.find { it.id == id }
    }

    fun resetState(){
        val history = uiState.history
        uiState = AppState(history = history)
    }

    fun isInputValid(): Boolean{
        val total = uiState.totalAmount.toDoubleOrNull() ?: return false
        val people = uiState.peopleCount.toIntOrNull() ?: return false
        val tip = uiState.tipPercent.toDoubleOrNull()

        return if (tip == null){
            total > 0 && people > 0
        } else{
            total>0 && people >0 && tip>0
        }

    }

}
