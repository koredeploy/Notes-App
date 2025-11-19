package com.bankaapp.data.repository

import com.bankaapp.data.di.AppPreference
import com.bankaapp.data.domain.model.SignupResponse
import com.bankaapp.data.domain.repository.SignupRepository
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignupRepositoryImp @Inject constructor(
    private val appPreference: AppPreference
) : SignupRepository {

    override suspend fun signUp(email: String, password: String): Result<SignupResponse> {
        return try {
            // Validate input
            if (!email.isBlank() && !password.isBlank()) {
                // Check if user already exists
                if (appPreference.isUserExists(email)) {
                    Result.success(
                        SignupResponse(
                            status = false,
                            message = "User with this email already exists"
                        )
                    )
                }
                else {
                    // Hash the password before saving
                    val hashedPassword = hashPassword(password)

                    // Save credentials
                    appPreference.saveUserCredentials(
                        email = email,
                        hashedPassword = hashedPassword
                    )

                    Result.success(
                        SignupResponse(
                            status = true,
                            message = "Account created successfully"
                        )
                    )
                }
            }else{
                return Result.success(
                    SignupResponse(
                        status = false,
                        message = "Email and password cannot be empty"
                    )
                )
            }
        } catch (e: Exception) {
            // Return failure Result with SignupResponse
            Result.success(
                SignupResponse(
                    status = false,
                    message = e.message ?: "An error occurred during signup"
                )
            )
        }
    }

    // Helper function to hash password
    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    // Helper function to validate email format
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}