package com.muhammedesadcomert.ecommerceapp.ui.login.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muhammedesadcomert.ecommerceapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    private var _auth: MutableLiveData<String> = MutableLiveData()
    val auth: LiveData<String> get() = _auth

    fun signUp(email: String, password: String) {
        authRepository.signUp(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _auth.postValue("Successful")
            } else {
                _auth.postValue(task.exception?.localizedMessage)
            }
        }
    }
}