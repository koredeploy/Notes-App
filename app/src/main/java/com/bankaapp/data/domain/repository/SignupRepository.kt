package com.bankaapp.data.domain.repository

import com.bankaapp.data.domain.model.SignupResponse

interface SignupRepository {
    suspend fun signUp(email: String, password: String): Result <SignupResponse>
}

