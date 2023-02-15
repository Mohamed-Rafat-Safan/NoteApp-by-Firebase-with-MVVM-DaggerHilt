package com.mohamedrafat.firebasewithmvvmapp.ui.AuthFragments


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mohamedrafat.firebasewithmvvmapp.data.model.Note
import com.mohamedrafat.firebasewithmvvmapp.data.model.User
import com.mohamedrafat.firebasewithmvvmapp.data.repository.AuthRepository
import com.mohamedrafat.firebasewithmvvmapp.data.repository.NoteRepository
import com.mohamedrafat.firebasewithmvvmapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val repository: AuthRepository
) : ViewModel() {

    private val _register = MutableLiveData<Resource<String>>()
    val register: LiveData<Resource<String>> get() = _register

    private val _login = MutableLiveData<Resource<String>>()
    val login: LiveData<Resource<String>> get() = _login

    private val _forgotPassword = MutableLiveData<Resource<String>>()
    val forgotPassword: LiveData<Resource<String>> get() = _forgotPassword


    val currentUser: FirebaseUser? get() = repository.currentUser


    init {
        if(currentUser != null){
            _login.value = Resource.Success("Logged In")
        }
    }



    fun registerUser(email: String, password: String, user: User) = viewModelScope.launch {
        _register.value = Resource.Loading
        repository.registerUser(email, password, user) {
            _register.value = it
        }
    }


    fun loginUser(email: String, password: String) = viewModelScope.launch {
        _login.value = Resource.Loading
        repository.loginUser(email, password) {
            _login.value = it
        }
    }


    fun forgotPassword(email: String) = viewModelScope.launch {
        _forgotPassword.value = Resource.Loading

        repository.forgetPassword(email) {
            _forgotPassword.value = it
        }
    }



    fun logout(result: () -> Unit){
        repository.logout(result)
    }

}

