package com.bankaapp.ui.screens.signup
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bankaapp.data.domain.repository.SignupRepository
import com.bankaapp.ui.screens.signup.SignupScreen
import com.bankaapp.ui.screens.signup.SignupScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepository
) : ViewModel() {

    private val _signupState = MutableStateFlow<SignupScreenState>(SignupScreenState.DefaultState)
    val signupState: StateFlow<SignupScreenState> = _signupState.asStateFlow()


    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _signupState.value = SignupScreenState.LoadingState

            try {
                signupRepository.signUp(email, password)
                    .onSuccess { response ->
                        if (response.status) {
                            _signupState.value = SignupScreenState.Success(response.message)


                            Log.i("SignupViewModel", "Signup successful: ${response.message}")
                        } else {
                            _signupState.value = SignupScreenState.Error(response.message)
                        }
                    }
                    .onFailure { exception ->
                        _signupState.value = SignupScreenState.Error(
                            exception.message ?: "Signup failed"
                        )
                    }
            } catch (e: Exception) {
                _signupState.value = SignupScreenState.Error(
                    "Network error: ${e.message ?: "Please check your connection"}"
                )
            }
        }
    }

    // Optional: Reset state when needed
    fun resetState() {
        _signupState.value = SignupScreenState.DefaultState
    }
}





//
//@HiltViewModel
//class SignupViewModel @Inject constructor(
//    private val signupRepository: SignupRepository
//) : ViewModel() {
//
//    private val _signupState = MutableStateFlow<SignupScreenState>(SignupScreenState.DefaultState)
//    val signupState: StateFlow<SignupScreenState> = _signupState.asStateFlow()
//
//    fun signUp(email: String, password: String) {
//        viewModelScope.launch {
//            _signupState.value = SignupScreenState.LoadingState
//
//
//
//            signupRepository.signUp(email, password)
//                .onSuccess { response ->
//                    if (response.status) {
//                        _signupState.value = SignupScreenState.Success(response.message)
//                    } else {
//                        _signupState.value = SignupScreenState.Error(response.message)
//                    }
//                }
//                .onFailure { exception ->
//                    _signupState.value = SignupScreenState.Error(
//                        exception.message ?: "Signup failed"
//                    )
//                }
//        }
//    }
//}
