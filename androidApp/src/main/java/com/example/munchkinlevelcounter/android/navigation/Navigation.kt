package com.example.munchkinlevelcounter.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.munchkinlevelcounter.android.ActivityViewModelProvider
import com.example.munchkinlevelcounter.android.presentation.detailedlist.DetailedMunchkinsListScreen
import com.example.munchkinlevelcounter.android.presentation.newmunchkin.CreateMunchkinScreen
import com.example.munchkinlevelcounter.android.presentation.settings.AppSettingsScreen
import com.example.munchkinlevelcounter.android.presentation.settings.LoadWebUrl
import com.example.munchkinlevelcounter.android.presentation.simplelist.SimpleMunchkinsListScreen
import com.example.munchkinlevelcounter.android.utils.fromArgs

@Composable
fun Navigation(viewModelProvider: ActivityViewModelProvider) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.SimpleMunchkinsListScreen.route
    ) {
        composable(route = Screens.SimpleMunchkinsListScreen.route) {
            SimpleMunchkinsListScreen(
                viewModel = viewModelProvider.munchkinViewModel(),
                onNavigate = { event ->
                    when (event) {
                        is NavigationEvent.Navigate -> navController.navigate(event.route)
                        else -> Unit
                    }
                }
            )
        }

        composable(
            route = Screens.CreateMunchkinScreen.route + "/{munchkin}",
            arguments = listOf(
                navArgument("munchkin") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            entry.arguments?.getString("munchkin")?.let { agrs ->
                CreateMunchkinScreen(
                    viewModel = viewModelProvider.munchkinViewModel(),
                    arguments = fromArgs(agrs),
                    onNavigate = { event ->
                        when (event) {
                            is NavigationEvent.PopBackStack -> navController.popBackStack()
                            else -> Unit
                        }
                    }
                )
            }
        }

        composable(
            route = Screens.DetailedMunchkinsListScreen.route + "/{currentMunchkin}",
            arguments = listOf(
                navArgument("currentMunchkin") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            entry.arguments?.getString("currentMunchkin")?.let { agrs ->
                DetailedMunchkinsListScreen(
                    viewModel = viewModelProvider.munchkinViewModel(),
                    arguments = fromArgs(agrs),
                    onNavigate = { event ->
                        when (event) {
                            is NavigationEvent.PopBackStack -> navController.popBackStack()
                            else -> Unit
                        }
                    }
                )
            }
        }

        composable(route = Screens.AppSettingsScreen.route) {
            AppSettingsScreen(
                viewModel = viewModelProvider.appSettingsViewModel(),
                onNavigate = { event ->
                    when (event) {
                        is NavigationEvent.PopBackStack -> navController.popBackStack()
                        is NavigationEvent.Navigate -> navController.navigate(event.route)
                        else -> Unit
                    }
                }
            )
        }

        composable(route = "webview") {
            LoadWebUrl()
        }
    }
}