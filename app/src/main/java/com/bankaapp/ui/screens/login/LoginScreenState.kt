package com.bankaapp.ui.screens.login

sealed class LoginScreenState {
    data object DefaultState : LoginScreenState()
    data object LoadingState : LoginScreenState()
    data class Success(val message : String) : LoginScreenState()
    data class Error(val message : String) : LoginScreenState()
}
