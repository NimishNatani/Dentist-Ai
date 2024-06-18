package com.practicecoding.dentalai.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.practicecoding.dentalai.data.AuthRepository
import com.practicecoding.dentalai.data.UserRepository
import com.practicecoding.dentalai.impl.AuthRepositoryImpl
import com.practicecoding.dentalai.impl.UserReposioryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthRepositoryModule {

    @Binds
    abstract fun providesFirebaseAuthRepository(
        repo: AuthRepositoryImpl
    ): AuthRepository
    @Binds
    abstract fun providesUserRepository(
        repo: UserReposioryImpl
    ): UserRepository
}