package com.example.fribet

class UserRepository {
    companion object{
        val instance = UserRepository()
    }
    lateinit var playerId: String

}