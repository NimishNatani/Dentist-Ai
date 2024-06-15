package com.practicecoding.dentalai.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.practicecoding.dentalai.Screens
import com.practicecoding.dentalai.data.AuthRepository
import com.practicecoding.dentalai.data.Resource
import com.practicecoding.dentalai.utils.showMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OtpCode -> signInWithCredential(
                event.code,
                event.context,
                event.navHostController
            )

            is AuthEvent.CreateUser -> createUserWithPhone(event.mobileNo,event.activity,event.context,event.navHostController)
            else -> {}
        }
    }

    private fun createUserWithPhone(
        mobileNo: String, activity: Activity, context: Context, navHostController: NavHostController
    ) {
        viewModelScope.launch {
            repo.createUserWithPhone(mobileNo, activity).collect{
                when (it) {
                    is Resource.Success -> {

                        context.showMsg(it.result)
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "phonenumberlogin",
                            value = mobileNo
                        )
                        navHostController.navigate(Screens.OtpScreen.route)
                    }

                    is Resource.Failure -> {

                        context.showMsg(it.exception.toString())
                    }

                    Resource.Loading -> {

                    }
                }
            }
        }
    }

    private fun signInWithCredential(
        code: String,
        context: Context,
        navHostController: NavHostController
    ) {
        viewModelScope.launch {
            repo.signWithCredential(code).collect {
                when (it) {
                    is Resource.Success -> {

                        context.showMsg(it.result)
                        navHostController.navigate(Screens.HomeScreen.route)
                    }

                    is Resource.Failure -> {

                        context.showMsg(it.exception.toString())
                    }

                    Resource.Loading -> {

                    }
                }
            }
        }
    }

}

sealed class AuthEvent {
    data class CreateUser(val mobileNo:String,val activity: Activity, val context: Context,
                          val navHostController: NavHostController) : AuthEvent()
    data class OtpCode(
        val code: String,
        val context: Context,
        val navHostController: NavHostController
    ) : AuthEvent()
}