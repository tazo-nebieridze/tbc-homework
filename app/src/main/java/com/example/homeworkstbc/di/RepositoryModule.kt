package com.example.homeworkstbc.di

import com.example.homeworkstbc.data.repositories.LogInRepositoryImpl
import com.example.homeworkstbc.data.repositories.RegisterRepositoryImpl
import com.example.homeworkstbc.domain.repository.LoginRepository
import com.example.homeworkstbc.domain.repository.RegisterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRegisterRepositoryImpl(
        registerRepositoryImpl: RegisterRepositoryImpl
    ) : RegisterRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepositoryImpl(
        loginRepositoryImpl: LogInRepositoryImpl
    ) : LoginRepository

}