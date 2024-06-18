package com.practicecoding.dentalai.data.model

data class UserModel( val name: String? = "",
                      val phoneNumber: String? = "",
                      var imageUri: String? = "",
                      val uid:String?="",
    val lowerteeth:String?="",
    val simileteeth:String?="",
    val upperteeth:String?="")
