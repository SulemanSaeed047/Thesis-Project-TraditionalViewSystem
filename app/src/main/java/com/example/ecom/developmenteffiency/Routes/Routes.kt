package com.example.ecom.developmenteffiency.Routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ecom.developmenteffiency.ViewModels.DEViewModel
import com.example.ecom.developmenteffiency.screens.AllUsersScreen
import com.example.ecom.developmenteffiency.screens.LoginScreen
import com.example.ecom.developmenteffiency.screens.MainScreen
import com.example.ecom.developmenteffiency.screens.PrevScreen
import com.example.ecom.developmenteffiency.screens.ProductHomeScreen
import com.example.ecom.developmenteffiency.screens.SettingScreen
import com.example.ecom.developmenteffiency.screens.SignUpScreen
import com.example.ecom.developmenteffiency.screens.SplashScreen
import com.example.ecom.developmenteffiency.screens.ThemeScreen
import com.example.ecom.developmenteffiency.screens.UserDetailScreen

sealed class Routes(
    val route: String,
) {
    data object ProductHomeScreen : Routes("ProductHomeScreen",)
    data object  PrevScreen : Routes("prevScreen/{productId}",){
        fun createRoute(productId: Int) = "prevScreen/$productId"
    }
    data object  UserDetailScreen : Routes("UserDetailScreen/{userId}",){
        fun createRoute(userId: Long) = "UserDetailScreen/$userId"
    }

    data object SplashScreen : Routes("SplashScreen",)
    data object LoginScreen : Routes("loginScreen",)
    data object SignUpScreen : Routes("signUpScreen",)
    data object MainScreen : Routes("MainScreen",)
    data object AllUsersScreen : Routes("AllUsersScreen",)
    data object ThemeScreen : Routes("ThemeScreen",)
    data object SettingScreen : Routes("SettingScreen",)

}

@Composable
fun AppNavigationController(
    navController: NavHostController,
    viewModel: DEViewModel,
) {

    NavHost(navController = navController, startDestination = Routes.SplashScreen.route) {
        composable(Routes.SplashScreen.route) {
            SplashScreen(moveToLogin = {
                navController.navigate(Routes.LoginScreen.route) {
                    popUpTo(Routes.SplashScreen.route) { inclusive = true }
                }
            })
        }
        composable(Routes.MainScreen.route) {
            MainScreen(
                moveTo = {
                    navController.navigate(it) {
                        launchSingleTop = true
                    }
                })
        }
        composable(Routes.AllUsersScreen.route) {
            AllUsersScreen(
                viewModel,
                onPrevClick =  {
                    navController.navigate(Routes.UserDetailScreen.createRoute(it)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Routes.ThemeScreen.route) {
            ThemeScreen(
                viewmodel = viewModel
            )
        }
        composable(Routes.SettingScreen.route) {
            SettingScreen(viewModel){
                navController.popBackStack()
            }
        }
        composable(Routes.ProductHomeScreen.route) {
            ProductHomeScreen(viewModel,
                onPrevClick = {
                    navController.navigate(Routes.PrevScreen.createRoute(it)) {
                        launchSingleTop = true
                    }
                })
        }

        composable(
            route = Routes.PrevScreen.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ){
            val prodId = it.arguments?.getInt("productId")
            PrevScreen(
                productId = prodId,
                viewModel = viewModel,
            )
        }
        composable(
            route = Routes.UserDetailScreen.route,
            arguments = listOf(navArgument("userId") { type = NavType.LongType })
        ){
            val prodId = it.arguments?.getLong("userId")
            UserDetailScreen(
                userId = prodId,
                viewModel = viewModel,
            )
        }


        composable(Routes.SignUpScreen.route) {
            SignUpScreen(
                viewmodel = viewModel,
                moveToLogin = {
                    navController.navigate(Routes.LoginScreen.route) {
                        launchSingleTop = true
                        popUpTo(Routes.SignUpScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.LoginScreen.route) {
            LoginScreen(
                viewmodel = viewModel,
                moveToHome = {
                    navController.navigate(Routes.MainScreen.route) {
                        launchSingleTop = true
                        popUpTo(Routes.LoginScreen.route) { inclusive = true }
                    }
                },
                moveToSignUp = {
                    navController.navigate(Routes.SignUpScreen.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}