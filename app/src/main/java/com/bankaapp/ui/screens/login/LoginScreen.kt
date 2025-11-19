
package com.bankaapp.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bankaapp.ui.components.CustomTextField
import com.bankaapp.ui.components.MediumSpace
import com.bankaapp.ui.components.SmallSpace
import com.bankaapp.ui.navigation.BankaScreens
import com.bankaapp.ui.theme.darkBlue
import com.bankaapp.core.util.Validator
import com.bankaapp.core.util.ValidationResult
import com.bankaapp.ui.screens.signup.SignupScreenState
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    vm: LoginViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var saveDetails by remember { mutableStateOf(false) }

    // Validation states
    var emailValidation by remember { mutableStateOf<ValidationResult>(ValidationResult.Valid) }
    var passwordValidation by remember { mutableStateOf<ValidationResult>(ValidationResult.Valid) }
    var showValidationErrors by remember { mutableStateOf(false) }

    // Loading state for login
    var isLoading by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    val loginState by vm.loginState.collectAsState()

    // Coroutine scope for async operations
    val coroutineScope = rememberCoroutineScope()

    // Check if form is valid
    val isFormValid = emailValidation is ValidationResult.Valid &&
            passwordValidation is ValidationResult.Valid &&
            email.isNotBlank() &&
            password.isNotBlank()

    fun validateForm(): Boolean {
        emailValidation = Validator.validateEmail(email)
        passwordValidation = Validator.validatePassword(password)
        showValidationErrors = true
        return isFormValid
    }

    fun handleLogin() {
        if (validateForm()) {
            vm.login(email, password)
        }
    }

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginScreenState.LoadingState -> {
                isLoading = true
            }
            is LoginScreenState.Success -> {
                isLoading = false
                navController.navigate(BankaScreens.NotesScreen) {
                    popUpTo(BankaScreens.LoginScreen) { inclusive = true }
                }
            }
            is LoginScreenState.Error -> {
                isLoading = false
                errorMessage = (loginState as LoginScreenState.Error).message
            }
            is LoginScreenState.DefaultState -> {
                isLoading = false
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {

        errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Text(
            text = "Welcome back!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(top = 60.dp)
        )

        Text(
            text = "Sign into your account",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 40.dp, top = 8.dp)
        )

        // Email Input
        Text(
            text = "Email Address",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        CustomTextField(
            value = email,
            onValueChange = {
                email = it
                if (showValidationErrors) {
                    emailValidation = Validator.validateEmail(it)
                }
            },
            placeholder = "justin_tobi89@example.com",
            inputType = Validator.InputType.EMAIL,
            validateOnChange = showValidationErrors
        )

        // Show email validation error
        if (showValidationErrors && emailValidation is ValidationResult.Invalid) {
            Text(
                text = (emailValidation as ValidationResult.Invalid).errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        MediumSpace()

        Text(
            text = "Password",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        SmallSpace()

        // Password Input
        CustomTextField(
            value = password,
            onValueChange = {
                password = it
                if (showValidationErrors) {
                    passwordValidation = Validator.validatePassword(it)
                }
            },
            placeholder = "Password",
            isPassword = true,
            inputType = Validator.InputType.PASSWORD,
            validateOnChange = showValidationErrors
        )

        // Show password validation error
        if (showValidationErrors && passwordValidation is ValidationResult.Invalid) {
            Text(
                text = (passwordValidation as ValidationResult.Invalid).errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        SmallSpace()

        // Forgot Password
        Text(
            text = "Forgot Password?",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { /* Handle forgot password */ }
        )

        MediumSpace()

        // Save Details Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Checkbox(
                checked = saveDetails,
                onCheckedChange = { saveDetails = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = darkBlue,
                    uncheckedColor = Color.Gray
                )
            )
            Text(
                text = "Save Details for later",
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        // Biometric Sign In
        OutlinedButton(
            onClick = { /* Handle biometric login */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = darkBlue
            )
        ) {
            Text(
                text = "Sign In with Biometric",
                fontSize = 16.sp
            )
        }

        MediumSpace()

        // Sign In Button
        Button(
            onClick = { handleLogin() },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = darkBlue,
                disabledContainerColor = darkBlue.copy(alpha = 0.5f)
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Continue",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}