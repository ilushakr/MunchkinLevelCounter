package com.example.munchkinlevelcounter.android.navigation

sealed class Screens(val route: String){
    object SimpleMunchkinsListScreen: Screens(route = "simple_munchkins_list_screen")
    object CreateMunchkinScreen: Screens(route = "create_munchkin_screen")
    object DetailedMunchkinsListScreen: Screens(route = "detailed_munchkins_list_screen")
    object AppSettingsScreen: Screens(route = "app_settings_screen")

    fun withArgs(vararg args: String) =
        buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
}