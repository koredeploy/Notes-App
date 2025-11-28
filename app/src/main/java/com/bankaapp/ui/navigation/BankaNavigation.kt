package com.bankaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute
import com.bankaapp.ui.screens.createnote.CreateNoteScreen
import com.bankaapp.ui.screens.login.LoginScreen

import com.bankaapp.ui.screens.notes.NoteDetailScreen
import com.bankaapp.ui.screens.notes.NotesScreen
import com.bankaapp.ui.screens.onboarding.OnboardingScreen
import com.bankaapp.ui.screens.signup.SignupScreen


@Composable
fun BankaNavigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = BankaScreens.OnboardingScreen){

        composable<BankaScreens.LoginScreen> {
            LoginScreen(navController = navController)
        }

        composable<BankaScreens.OnboardingScreen> {
            OnboardingScreen( navController = navController)}


        composable<BankaScreens.SignupScreen> {
            SignupScreen(navController = navController)
        }

        composable<BankaScreens.NotesScreen> {
            NotesScreen(navController = navController)
        }

        composable <BankaScreens.CreateNoteScreen>{
            CreateNoteScreen(navController = navController)
        }

        composable<BankaScreens.NotesDetailScreen> { backStackEntry ->
            val noteDetailScreen = backStackEntry.toRoute<BankaScreens.NotesDetailScreen>()
            NoteDetailScreen(
                navController = navController,
                noteId = noteDetailScreen.noteId
            )
        }

    }

}


