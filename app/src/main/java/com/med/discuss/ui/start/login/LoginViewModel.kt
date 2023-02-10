package com.med.discuss.ui.start.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.med.discuss.data.model.Login
import com.med.discuss.data.db.repository.AuthRepository
import com.med.discuss.ui.DefaultViewModel
import com.med.discuss.data.Event
import com.med.discuss.data.Result
import com.med.discuss.util.isEmailValid
import com.med.discuss.util.isTextValid
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : DefaultViewModel() {

    private val authRepository = AuthRepository()
    private val _isLoggedInEvent = MutableLiveData<Event<FirebaseUser>>()

    val isLoggedInEvent: LiveData<Event<FirebaseUser>> = _isLoggedInEvent
    val emailText = MutableLiveData<String>()
    val passwordText = MutableLiveData<String>()
    val isLoggingIn = MutableLiveData<Boolean>()

    private fun login() {
        isLoggingIn.value = true
        val login = Login(emailText.value!!, passwordText.value!!)

        authRepository.loginUser(login) { result: Result<FirebaseUser> ->
            onResult(null, result)
            if (result is Result.Success) _isLoggedInEvent.value = Event(result.data!!)
            if (result is Result.Success || result is Result.Error){
                isLoggingIn.value = false
                if (result is Result.Error) {
                    throw IllegalArgumentException("error login!")
                }
            }
        }
    }

    fun loginPressed() {
//        if (!isEmailValid(emailText.value.toString())) {
//            mSnackBarText.value = Event("Invalid email format")
//            return
//        }
        if (!isTextValid(6, passwordText.value)) {
            mSnackBarText.value = Event("Password is too short")
            return
        }

        login()
    }
}