package com.bankaapp.data.domain.usecase

import com.bankaapp.data.domain.repository.LoginRepository
import javax.inject.Inject

class GetAllLoginUseCase @Inject constructor (private val loginRepository: LoginRepository){
    suspend fun login(email: String, password: String) = loginRepository.login(email, password)

}

