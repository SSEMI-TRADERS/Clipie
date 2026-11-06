package com.clipie.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.clipie.app.presentation.SessionViewModel
import com.clipie.authentication.presentation.AuthenticationViewModel
import com.clipie.authentication.presentation.forgot_password.ForgotPasswordScreen
import com.clipie.authentication.presentation.login.LoginScreen
import com.clipie.authentication.presentation.navigation.AuthenticationNavConstant
import com.clipie.authentication.presentation.register.RegistrationScreen
import com.clipie.main.presentation.MainScreen
import com.clipie.util.SessionState

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val sessionViewModel: SessionViewModel = hiltViewModel()
    val sessionState by sessionViewModel.sessionState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = AppNavConstant.Authentication.route,
        modifier = modifier
    ) {
        navigation(
            startDestination = AuthenticationNavConstant.Login.route,
            route = AppNavConstant.Authentication.route
        ) {
            composable(route = AuthenticationNavConstant.Login.route) {
                val viewModel =
                    it.sharedViewModel<AuthenticationViewModel>(navController = navController)
                LoginScreen(navController = navController, viewModel = viewModel)
            }
            composable(route = AuthenticationNavConstant.Register.route) {
                val viewModel =
                    it.sharedViewModel<AuthenticationViewModel>(navController = navController)
                RegistrationScreen(navController = navController, viewModel = viewModel)
            }
            composable(route = AuthenticationNavConstant.ForgotPassword.route) {
                val viewModel =
                    it.sharedViewModel<AuthenticationViewModel>(navController = navController)
                ForgotPasswordScreen(navController = navController, viewModel = viewModel)
            }
        }
        composable(route = AppNavConstant.Main.route) {
            MainScreen()
        }
    }

    LaunchedEffect(sessionState) {
        val targetRoute = when (sessionState) {
            SessionState.Initializing -> return@LaunchedEffect
            SessionState.SignedOut -> AppNavConstant.Authentication.route
            is SessionState.SignedIn -> AppNavConstant.Main.route
        }

        val alreadyInTarget = navController.currentDestination
            ?.hierarchy
            ?.any { it.route == targetRoute} == true

        if (!alreadyInTarget) {
            navController.navigate(targetRoute) {
                popUpTo(navController.graph.id) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
