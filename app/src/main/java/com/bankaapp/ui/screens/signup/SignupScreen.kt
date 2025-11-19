package com.bankaapp.ui.screens.signup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bankaapp.core.util.ValidationResult
import com.bankaapp.core.util.Validator
import com.bankaapp.ui.components.CustomTextField
import com.bankaapp.ui.components.MediumSpace
import com.bankaapp.ui.navigation.BankaScreens
import com.bankaapp.ui.screens.login.LoginViewModel
import com.bankaapp.ui.theme.darkBlue
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(navController: NavController, vm: SignupViewModel = hiltViewModel() ) {

    var email by remember { mutableStateOf(" ") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf( "") }
    var saveDetails by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }


    // Validation states
    var emailValidation by remember { mutableStateOf<ValidationResult>(ValidationResult.Valid) }
    var passwordValidation by remember { mutableStateOf<ValidationResult>(ValidationResult.Valid) }
    var phoneValidation by remember { mutableStateOf<ValidationResult>(ValidationResult.Valid) }
    var confirmPasswordValidation by remember { mutableStateOf<ValidationResult>(ValidationResult.Valid) }
    var showValidationErrors by remember { mutableStateOf(false) }

    // Loading state for login
    var isLoading by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    val signupState by vm.signupState.collectAsState()


    // Check if form is valid
    val isFormValid = emailValidation is ValidationResult.Valid &&
            passwordValidation is ValidationResult.Valid &&
            email.isNotBlank() &&
            password.isNotBlank()


    fun validateForm(): Boolean {
        emailValidation = Validator.validateEmail(email)
        passwordValidation = Validator.validatePassword(password)
        phoneValidation = Validator.validatePhoneNumber(phone)
        confirmPasswordValidation = Validator.validateConfirmPassword(password, confirmPassword)
        showValidationErrors = true
        return isFormValid
    }

    fun handleSignup() {
        if (validateForm()) {
            vm.signUp(email, password)
        }
    }

    LaunchedEffect(signupState) {
        when (signupState ) {
            is SignupScreenState.LoadingState -> {
                isLoading = true

            }
            is SignupScreenState.Success -> {
                isLoading = false
                navController.navigate(BankaScreens.LoginScreen) {
                    popUpTo(BankaScreens.SignupScreen) { inclusive = true }
                }
            }
            is SignupScreenState.Error -> {
                isLoading = false
                errorMessage = (signupState as SignupScreenState.Error).message
            }
            is SignupScreenState.DefaultState -> {
                isLoading = false
            }
        }
    }

//    wisdom4me1@gmail.com  P@ssword90

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                modifier = Modifier.padding(top = 25.dp, bottom = 10.dp).fillMaxSize()
                    .fillMaxWidth(),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                IconButton(
                    modifier = Modifier.offset(x = (-15).dp),
                    onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                Text(
                    text = "Back",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            errorMessage?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Text(
                text = "Let's get you started!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "Your password must have at least 8 characters including Letters and Numbers",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                modifier = Modifier.padding(top = 5.dp)
            )

            MediumSpace()

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
                text = "Phone ",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )


            CustomTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    if (showValidationErrors) {
                        phoneValidation = Validator.validatePhoneNumber(it)
                    }
                },
                placeholder = "+2348167882345",
                inputType = Validator.InputType.PHONE,
                validateOnChange = showValidationErrors
            )

            // Show Phone validation error
            if (showValidationErrors && phoneValidation is ValidationResult.Invalid) {
                Text(
                    text = (phoneValidation as ValidationResult.Invalid).errorMessage,
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

            MediumSpace()

            Text(
                text = "Confirm Password",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            CustomTextField(
                value = confirmPassword,  // Fixed!
                onValueChange = {
                    confirmPassword = it
                    if (showValidationErrors) {
                        confirmPasswordValidation = Validator.validateConfirmPassword(password, it)
                    }
                },
                isPassword = true,
                placeholder = "........."
            )

            // Show password validation error
            if (showValidationErrors && confirmPasswordValidation is ValidationResult.Invalid) {
                Text(
                    text = (confirmPasswordValidation as ValidationResult.Invalid).errorMessage,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                )
            }
        }



        BottomAppBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp, vertical = 20.dp),
            containerColor = Color.Transparent
        ) {
            Button(
                onClick = { handleSignup()},
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
}



//
//    fun handleSignup() {
//        if (validateForm()) {
//            coroutineScope.launch {
//                isLoading = true
//                try {
//
//                    vm.signUp(email, password)
//
//                    // Simulate
//                    kotlinx.coroutines.delay(1000)
//
//
//                    // Navigate on success
//                    navController.navigate(BankaScreens.LoginScreen)
//                } catch (e: Exception) {
//                    // Handle login error
//
//                } finally {
//                    isLoading = false
//                }
//            }
//        }
//    }