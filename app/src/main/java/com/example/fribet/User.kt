package com.example.fribet

data class User(
    val username: String? = " ",
    val email: String? = " ",
    val userId: String? = " ",
    val totalBets: Int? = 0,
    val wins: Int? = 0,
    val losses: Int? = 0,
    val totalBalance: Int? = 0,
    val friends: ArrayList<String>? = ArrayList(),
    val invites: ArrayList<String>? = ArrayList(),
    val currentBets: ArrayList<String>? = ArrayList(),
    val historyBets: ArrayList<String>? = ArrayList()
)

