package com.bankaapp.data.domain.model


data class SignupResponse(val status: Boolean, val message: String)

data class SignupRequest(val email: String, val password: String)

