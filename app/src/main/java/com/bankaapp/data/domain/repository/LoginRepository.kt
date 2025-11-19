package com.bankaapp.data.domain.repository

import com.bankaapp.data.domain.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(email: String, password: String): Result <LoginResponse>
}
