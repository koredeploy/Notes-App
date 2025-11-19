package com.bankaapp.core.util

import android.util.Patterns

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val errorMessage: String) : ValidationResult()
}

class Validator {

    companion object {
        // Email validation using Android's built-in pattern
//        fun validateEmail(email: String): ValidationResult {
//            return when {
//                email.isBlank() -> ValidationResult.Invalid("Email cannot be empty")
//                !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
//                    ValidationResult.Invalid("Invalid email format")
//                else -> ValidationResult.Valid
//            }
//        }
        fun validateEmail(email: String): ValidationResult {
            // Comprehensive email regex pattern
            val emailRegex = """^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$""".toRegex()

            return when {
                email.isBlank() -> ValidationResult.Invalid("Email cannot be empty")
                !emailRegex.matches(email) ->
                    ValidationResult.Invalid("Invalid email format")
                else -> ValidationResult.Valid
            }
        }

        // Password validation for strong passwords
        fun validatePassword(password: String): ValidationResult {
            val minLength = 8
            val hasUpperCase = password.any { it.isUpperCase() }
            val hasLowerCase = password.any { it.isLowerCase() }
            val hasDigit = password.any { it.isDigit() }
            val hasSpecialChar = password.any { !it.isLetterOrDigit() }

            return when {
                password.isBlank() ->
                    ValidationResult.Invalid("Password cannot be empty")
                password.length < minLength ->
                    ValidationResult.Invalid("Password must be at least $minLength characters")
                !hasUpperCase ->
                    ValidationResult.Invalid("Password must contain at least one uppercase letter")
                !hasLowerCase ->
                    ValidationResult.Invalid("Password must contain at least one lowercase letter")
                !hasDigit ->
                    ValidationResult.Invalid("Password must contain at least one digit")
                !hasSpecialChar ->
                    ValidationResult.Invalid("Password must contain at least one special character")
                else -> ValidationResult.Valid
            }
        }

        // Phone number validation (supports international formats)
        fun validatePhoneNumber(phoneNumber: String): ValidationResult {
            // Remove all non-digit characters for validation
            val digitsOnly = phoneNumber.filter { it.isDigit() }

            // Basic international phone number regex pattern
            // Supports formats like: +1234567890, +12-345-678-9012, (123) 456-7890, etc.
            val phoneRegex = """^[\+]?[(]?[0-9]{1,3}[)]?[-\s\.]?[(]?[0-9]{1,4}[)]?[-\s\.]?[0-9]{1,4}[-\s\.]?[0-9]{1,9}$""".toRegex()

            return when {
                phoneNumber.isBlank() ->
                    ValidationResult.Invalid("Phone number cannot be empty")
                digitsOnly.length < 10 ->
                    ValidationResult.Invalid("Phone number must contain at least 10 digits")
                digitsOnly.length > 15 ->
                    ValidationResult.Invalid("Phone number is too long")
                !phoneRegex.matches(phoneNumber) ->
                    ValidationResult.Invalid("Invalid phone number format")
                else -> ValidationResult.Valid
            }
        }

        // Generic text validation (for regular text fields)
        fun validateText(text: String, fieldName: String, minLength: Int = 1): ValidationResult {
            return when {
                text.isBlank() ->
                    ValidationResult.Invalid("$fieldName cannot be empty")
                text.length < minLength ->
                    ValidationResult.Invalid("$fieldName must be at least $minLength characters")
                else -> ValidationResult.Valid
            }
        }

        //Confirm Validate Password
        fun validateConfirmPassword(password: String, confirmPassword: String): ValidationResult {
            return when {
                confirmPassword.isBlank() ->
                    ValidationResult.Invalid("Please confirm your password")
                password != confirmPassword ->
                    ValidationResult.Invalid("Passwords do not match")
                else -> ValidationResult.Valid
            }
        }

        // Auto-detect input type and validate accordingly
        fun autoValidate(input: String, inputType: InputType): ValidationResult {
            return when (inputType) {
                InputType.EMAIL -> validateEmail(input)
                InputType.PASSWORD -> validatePassword(input)
                InputType.PHONE -> validatePhoneNumber(input)
                InputType.TEXT -> validateText(input, "Field")
                InputType.CONFIRM_PASSWORD -> validateConfirmPassword( input, input)
            }
        }
    }




    enum class InputType {
        EMAIL,
        PASSWORD,
        PHONE,
        TEXT,
        CONFIRM_PASSWORD

    }
}