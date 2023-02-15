package com.mohamedrafat.firebasewithmvvmapp.data.repository


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.mohamedrafat.firebasewithmvvmapp.data.model.User
import com.mohamedrafat.firebasewithmvvmapp.util.Constants.Companion.USER_TABLE
import com.mohamedrafat.firebasewithmvvmapp.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class AuthRepositoryImpl(
    val auth: FirebaseAuth,
    val database: FirebaseFirestore
) : AuthRepository {

    override val currentUser: FirebaseUser? get() = auth.currentUser

    override suspend fun registerUser(
        email: String,
        password: String,
        user: User,
        result: (Resource<String>) -> Unit
    ) {

        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            updateUserInfo(user) { resource ->
                when (resource) {
                    is Resource.Success -> {
                        result.invoke(Resource.Success("User register successfully!"))
                    }
                    is Resource.Failure -> {
                        result.invoke(Resource.Failure(resource.error))
                    }
                    else -> {}
                }
            }
        } catch (e: Exception) {
            result.invoke(Resource.Failure(e.message))
        }
    }

    override suspend fun updateUserInfo(user: User, result: (Resource<String>) -> Unit) {
        val document = database.collection(USER_TABLE).document()
        user.id = document.id
        document.set(user).addOnSuccessListener {
            result.invoke(Resource.Success("user is updated"))
        }.addOnFailureListener {
            result.invoke(Resource.Failure(it.message))
        }
    }

    override suspend fun loginUser(
        email: String,
        password: String,
        result: (Resource<String>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            result.invoke(Resource.Success("User logged in successfully!"))
        }.addOnFailureListener {
            result.invoke(Resource.Failure(it.message))
        }
    }


    override suspend fun forgetPassword(email: String, result: (Resource<String>) -> Unit) {
        auth.sendPasswordResetEmail(email).addOnSuccessListener {
            result.invoke(Resource.Success("Email has been sent"))
        }.addOnFailureListener {
            result.invoke(Resource.Failure(it.message))
        }
    }


    override fun logout(result: () -> Unit) {
        auth.signOut()
        result.invoke()
    }
}

