package com.practicecoding.dentalai.data

import android.content.Context
import android.net.Uri
import com.practicecoding.dentalai.data.model.UserModel

interface UserRepository {

    suspend fun getUser():UserModel

    suspend fun uploadImage(uri: Uri,
                            context: Context,
                            onSuccess: () -> Unit,
                            onFailure: (Exception) -> Unit,idx:Int)
}