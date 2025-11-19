package com.bankaapp.ui.screens.login
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bankaapp.data.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginScreenState>(LoginScreenState.DefaultState)
    val loginState: StateFlow<LoginScreenState> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginScreenState.LoadingState

            loginRepository.login(email, password)
                .onSuccess { response ->
                    if (response.status) {
                        Log.i( password, email)

                        Log.i("LoginViewModel", "Login successful: ${response.message}")
                        _loginState.value = LoginScreenState.Success(
                            message = response.message,
//                            email = response.email
                        )
                    } else {
                        _loginState.value = LoginScreenState.Error(response.message)
                    }
                }
                .onFailure { exception ->
                    _loginState.value = LoginScreenState.Error(

                        exception.message ?: "Login failed"

                    )
                }
        }
    }
}

