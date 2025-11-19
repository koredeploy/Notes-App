package com.bankaapp.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bankaapp.R
import com.bankaapp.ui.components.LargeSpace
import com.bankaapp.ui.navigation.BankaScreens

@Composable
fun OnboardingScreen(
navController: NavController
) {

    fun onSignInClick(){
        navController.navigate(BankaScreens.LoginScreen)
        // Handle the click event here
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bankalogo),
            contentDescription = "Banka Logo",
            modifier = Modifier
                .size(190.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )

        LargeSpace()

        Text(
            text = stringResource(R.string.get_your_money_under_control),
            color = Color.Black,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.manage_your_money_seamlessly), // Fixed typo
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(200.dp))

        Button(
            onClick = {navController.navigate(BankaScreens.SignupScreen)},
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E3A8A)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Get Started",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text(
                text = stringResource(R.string.already_have_an_account),
                color = Color.Gray
            )
            Text(
                text = "Sign In",
                color = Color(0xFF1E3A8A),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onSignInClick() }
            )
        }
    }
}

