package com.bankaapp.ui.screens.signup

sealed class SignupScreenState {
    data object DefaultState : SignupScreenState()
    data object LoadingState : SignupScreenState()
    data class Success(val message : String) : SignupScreenState()
    data class Error(val message : String) : SignupScreenState()
}
