package com.example.fribet

data class Bets(
    val amount: Int? = 0,
    val accepted: Boolean? = false,
    val completed: Boolean? = false,
    val description: String? = " ",
    val playerSending: String? = "",
    val playerReceiving: String? = ""
)