package com.practicecoding.dentalai.data

import android.app.Activity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
        fun createUserWithPhone(
            phone:String,activity: Activity
        ) : Flow<Resource<String>>

        fun signWithCredential(
            otp:String
        ): Flow<Resource<String>>

        suspend fun setUser(name:String, phone: String)

    }
