package com.example.findmyteam.helpers

import com.example.findmyteam.models.User

interface UsersListener {
    fun onUsersReceived(users: List<User>?)
    fun onError(error: Throwable)
}