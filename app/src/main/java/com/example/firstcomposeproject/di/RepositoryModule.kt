package com.example.firstcomposeproject.di


import com.example.firstcomposeproject.data.repositories.LogInRepositoryImpl
import com.example.firstcomposeproject.data.repositories.RegisterRepositoryImpl
import com.example.firstcomposeproject.data.repositories.UserRepositoryImpl
import com.example.firstcomposeproject.domain.repository.LoginRepository
import com.example.firstcomposeproject.domain.repository.RegisterRepository
import com.example.firstcomposeproject.domain.repository.UserRepository
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


    @Binds
    @Singleton
    abstract fun bindUserRepositoryImpl(
        userRepositoryImpl: UserRepositoryImpl
    ) : UserRepository

}