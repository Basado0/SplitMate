package com.example.splitmate.models

data class Calculation(
    val id: Long = System.currentTimeMillis(),
    val totalAmount: Double = 0.0,
    val peopleCount: Int = 1,
    val tipPercent: Double? = null
) {
    val tipAmount: Double
        get() = tipPercent?.let { totalAmount * (it / 100) } ?: 0.0

    val totalWithTip: Double
        get() = totalAmount + tipAmount

    val perPerson: Double
        get() = if (peopleCount > 0) totalWithTip / peopleCount else 0.0
}


data class AppState(

    val totalAmount: String = "",
    val peopleCount: String = "1",
    val tipPercent: String = "",

    val currentCalculation: Calculation? = null,

    val history: List<Calculation> = emptyList()
)