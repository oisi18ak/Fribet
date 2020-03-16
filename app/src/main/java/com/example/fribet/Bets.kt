package com.example.fribet

data class Bets(
    val accepted: Boolean? = false,
    val amount: Int? = 0,
    var betID: String? = " ",
    val completed: Boolean? = false,
    val description: String? = " ",
    val playerReceiving: String? = "",
    val playerSending: String? = ""

)