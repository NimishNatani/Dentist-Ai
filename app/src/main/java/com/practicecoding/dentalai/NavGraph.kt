package com.practicecoding.dentalai

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.practicecoding.dentalai.screen.HorizontalScreen
import com.practicecoding.dentalai.screen.LoginScreen
import com.practicecoding.dentalai.screen.MainScreen
import com.practicecoding.dentalai.screen.OtpScreen
import com.practicecoding.dentalai.screen.StartScreen
import com.practicecoding.dentalai.screen.category.ReportScreen
import com.practicecoding.dentalai.screen.category.SymptomsTracker
import com.practicecoding.dentalai.screen.category.reportscreen.CameraPreviewScreen
import com.practicecoding.dentalai.screen.category.reportscreen.ScanTeethScreen
import com.practicecoding.dentalai.screen.category.reportscreen.TeethImageScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetUpNavGraph(
    navController: NavHostController
) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = if (FirebaseAuth.getInstance().currentUser==null)Screens.StartScreen.route else Screens.HomeScreen.route) {
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
            val phoneNumber =
                navController.previousBackStackEntry?.savedStateHandle?.get<String>("phonenumberlogin")
                    .toString()
            val name = navController.previousBackStackEntry?.savedStateHandle?.get<String>("name")
                .toString()
            OtpScreen(phoneNumber, name, navController)
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
            ReportScreen()
        }
        composable(
            route = Screens.SymptomsTracker.route
        ) {
            SymptomsTracker()
        }
        composable(
            route = Screens.CameraScreen.route
        ) {
            CameraPreviewScreen(navHostController = navController
                // Handle the captured photo URI here
            )
        }
        composable(
            route = Screens.TeethImageScreen.route
        ) {
            TeethImageScreen()
        }
    }
}