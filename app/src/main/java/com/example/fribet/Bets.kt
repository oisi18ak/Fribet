package com.example.fribet

data class Bets(
    val playerSending: String,
    val playerReceiving: String,
    val description: String? = " ",
    val amount: Int? = 0,
    val accepted: Boolean? = false,
    val completed: Boolean? = false
)