package com.practicecoding.dentalai

sealed class Screens(val route: String) {
   data object StartScreen:Screens(route = "start_screen")
   data object LoginScreen:Screens(route = "login_screen")
   data object OtpScreen:Screens(route = "otp_screen")
   data object HorizontalScreen:Screens(route = "horizontal_screen")
   data object HomeScreen:Screens(route = "home_screen")
   data object ScanTeethScreen:Screens(route = "scan_teeth_screen")
   data object ReportScreen:Screens(route = "report_screen")
   data object SymptomsTracker:Screens(route = "symptoms_screen")
   data object CameraScreen:Screens(route = "camera_screen")
   data object TeethImageScreen:Screens(route = "teethImageScreen")

}