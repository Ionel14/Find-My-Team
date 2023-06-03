package com.example.findmyteam.helpers

import android.content.Context
import com.example.findmyteam.data.UsersManagement
import com.example.findmyteam.models.User
import java.util.concurrent.CompletableFuture

fun logInChecks(context: Context, email:String, password:String): Boolean {
    if (email.isNullOrEmpty())
    {
        showInvalidDialog( "Email Field is empty","Please enter the email", context)
        return false
    }

    if (!email.contains('@'))
    {
        showInvalidDialog( "This is not a valid email","Please enter a valid email", context)
        return false

    }

    if (password.isNullOrEmpty())
    {
        showInvalidDialog( "Password Field is empty","Please enter the password", context)
        return false

    }

    return true
}

fun existentUserCheck(context: Context, email: String, callback: (Boolean) -> Unit) {
    val userFuture: CompletableFuture<User> = UsersManagement.findUser(context, email)

    userFuture.thenAccept { user ->
        if (user != null) {
            showInvalidDialog("This email is already used", "Please enter another email", context)
            callback(true)
        } else {
            callback(false)
        }
    }.exceptionally { ex ->
        // Error handling logic
        println("An error occurred: ${ex.message}")
        callback(false)
        null
    }
}


fun signInChecks(context: Context, email:String, password:String, firstname: String, lastname: String): Boolean {

    if (!logInChecks(context, email, password))
    {
        return false
    }

    if (firstname.isNullOrEmpty())
    {
        showInvalidDialog( "Firstname Field is empty","Please enter your firstname", context)
        return false

    }

    if (lastname.isNullOrEmpty())
    {
        showInvalidDialog( "Lastname Field is empty","Please enter your lastname", context)

        return false

    }

    return true
}