package com.bankaapp.ui.navigation

import kotlinx.serialization.Serializable

sealed class BankaScreens : Route {

    @Serializable
    data object OnboardingScreen : BankaScreens()

    @Serializable
    data object LoginScreen : BankaScreens()

    @Serializable
    data object SignupScreen : BankaScreens()

    @Serializable
    data object NotesScreen : BankaScreens()

    @Serializable
    data object CreateNoteScreen : BankaScreens()


}