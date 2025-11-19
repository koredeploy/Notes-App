package com.bankaapp.data.domain.usecase

import com.bankaapp.data.domain.repository.SignupRepository
import javax.inject.Inject

class GetAllSignupUseCase @Inject constructor (private val signupRepository: SignupRepository){
    suspend fun signup(email: String, password: String) = signupRepository.signUp(email, password)
}

