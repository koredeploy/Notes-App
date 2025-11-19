package com.bankaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.bankaapp.ui.navigation.BankaNavigation
import com.bankaapp.ui.theme.BankaappTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.internal.ThreadSafeHeap

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            BankaappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BankaContent(innerPadding)
                }
            }
        }
    }
}

@Composable
fun BankaContent(paddingValues: PaddingValues){
    val navController = rememberNavController()
    BankaNavigation(navController = navController)
}


