package com.example.fribet

class BetRepository {
    companion object{
        val instance = BetRepository()
    }
    var listOfBets = mutableListOf<Bets>()
    var listOfAcceptedBets = mutableListOf<Bets>()
    var listOfUnacceptedBets = mutableListOf<Bets>()
}