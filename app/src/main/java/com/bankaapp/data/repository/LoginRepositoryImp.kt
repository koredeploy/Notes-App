package com.bankaapp.data.repository

import com.bankaapp.data.di.AppPreference
import com.bankaapp.data.domain.model.LoginResponse
import com.bankaapp.data.domain.repository.LoginRepository
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImp @Inject constructor(
    private val appPreference: AppPreference
) : LoginRepository {

    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            // Validate input
            when {
                email.isBlank() || password.isBlank() -> {
                    Result.success(
                        LoginResponse(
                            status = false,
                            message = "Email and password cannot be empty"
                        )
                    )
                }
                !isValidEmail(email) -> {
                    Result.success(
                        LoginResponse(
                            status = false,
                            message = "Please enter a valid email address"
                        )
                    )
                }
                else -> {
                    val hashedPassword = hashPassword(password)
                    val isValid = appPreference.verifyCredentials(email, hashedPassword)

                    if (isValid) {
//                        appPreference.setLoginStatus(true)
                        Result.success(
                            LoginResponse(
                                status = true,
                                message = "Login successful",
                            )
                        )
                    } else {
                        Result.success(
                            LoginResponse(
                                status = false,
                                message = "Invalid email or password"
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Result.success(
                LoginResponse(
                    status = false,
                    message = e.message ?: "An error occurred during login"
                )
            )
        }
    }

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}