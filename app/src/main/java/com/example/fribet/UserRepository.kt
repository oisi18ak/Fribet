package com.example.fribet

class UserRepository {
    companion object{
        val instance = UserRepository()
    }
    var currentUserId: String? = " "
}