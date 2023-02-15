package com.mohamedrafat.firebasewithmvvmapp.data.repository

import com.google.firebase.auth.FirebaseUser
import com.mohamedrafat.firebasewithmvvmapp.data.model.User
import com.mohamedrafat.firebasewithmvvmapp.util.Resource

interface AuthRepository {

    val currentUser: FirebaseUser?

    suspend fun registerUser(
        email: String,
        password: String,
        user: User,
        result: (Resource<String>) -> Unit
    )

    suspend fun updateUserInfo(user: User, result: (Resource<String>) -> Unit)

    suspend fun loginUser(
        email: String,
        password: String,
        result: (Resource<String>) -> Unit
    )

    suspend fun forgetPassword(email: String, result: (Resource<String>) -> Unit)

    fun logout(result: () -> Unit)

}
