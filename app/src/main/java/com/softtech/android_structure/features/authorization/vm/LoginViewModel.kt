package com.softtech.android_structure.features.authorization.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.Transformations
import com.softtech.android_structure.R
import com.softtech.android_structure.domain.usecases.account.UserUseCase
import com.softtech.android_structure.entities.account.LoginParameters
import com.softtech.android_structure.entities.account.User
import com.softtech.android_structure.features.common.CommonState

class LoginViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()

    private val _login = MutableLiveData<LoginParameters>()

    var loginState=MutableLiveData<CommonState<User>>()
    lateinit var  loginStateLiveData:LiveData<CommonState<User>>

    fun login(loginParameters: LoginParameters){
        //loginState  = userUseCase.login(loginParameters)

    }



    fun loginDataChanged(username: String, password: String) {

        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value =
                LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value =
                LoginFormState(isDataValid = true)
        }
    }


    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5;
    }

    val user: LiveData<CommonState<User>> = Transformations
        .switchMap(_login) { login ->
           userUseCase.login(login)
        }

    fun setLogin(login: LoginParameters?) {
        if (_login.value != login) {
            _login.value = login
        }
    }

}
