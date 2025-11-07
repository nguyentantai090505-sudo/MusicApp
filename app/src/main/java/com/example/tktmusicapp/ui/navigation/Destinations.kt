package com.example.tktmusicapp.ui.navigation

object Destinations {
    // Auth Flow
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val CHOOSE_ARTIST = "choose_artist"

    // Main App
    const val MAIN = "main"
    const val HOME = "home"
    const val SEARCH = "search"        // ✅ thêm dòng này
    const val PLAYER = "player"        // ✅ nếu có PlayerScreen
    const val PROFILE = "profile"      // ✅ nếu có ProfileScreen
    const val LIBRARY = "library"      // ✅ nếu có LibraryScreen

    // Graph Routes
    const val AUTH_GRAPH = "auth"
    const val MAIN_GRAPH = "main"
}
