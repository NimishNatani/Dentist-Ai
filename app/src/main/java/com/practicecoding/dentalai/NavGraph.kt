package com.practicecoding.dentalai

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.practicecoding.dentalai.screen.HomeScreen
import com.practicecoding.dentalai.screen.HorizontalScreen
import com.practicecoding.dentalai.screen.LoginScreen
import com.practicecoding.dentalai.screen.MainScreen
import com.practicecoding.dentalai.screen.OtpScreen
import com.practicecoding.dentalai.screen.StartScreen
import com.practicecoding.dentalai.screen.category.ReportScreen
import com.practicecoding.dentalai.screen.category.ScanTeethScreen
import com.practicecoding.dentalai.screen.category.SymptomsTracker

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetUpNavGraph(
    navController: NavHostController
) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = Screens.StartScreen.route) {
        composable(
            route = Screens.StartScreen.route
        ) {
            StartScreen(navController)
        }
        composable(
            route = Screens.LoginScreen.route
        ) {

            LoginScreen(navController, activity = context as Activity)
        }
        composable(
            route = Screens.OtpScreen.route
        ) {
            val phoneNumber=
                    navController.previousBackStackEntry?.savedStateHandle?.get<String>("phonenumberlogin")
                        .toString()
            OtpScreen(phoneNumber, navController)
        }
        composable(
            route = Screens.HorizontalScreen.route
        ) {
            HorizontalScreen()
        }
        composable(
            route = Screens.HomeScreen.route
        ) {
            MainScreen(navHostController = navController)
        }
        composable(
            route = Screens.ScanTeethScreen.route
        ) {
            ScanTeethScreen(navHostController = navController)
        }
        composable(
            route = Screens.ReportScreen.route
        ) {
            ReportScreen( )
        }
        composable(
            route = Screens.SymptomsTracker.route
        ) {
            SymptomsTracker( )
        }
    }
}