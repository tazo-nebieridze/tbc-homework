// AppModule.kt
package com.example.homeworkstbc.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.homeworkstbc.BuildConfig
import com.example.homeworkstbc.client.UserService
import com.example.homeworkstbc.roomDatabase.AppDatabase
import com.example.homeworkstbc.roomDatabase.UserDb
import com.example.homeworkstbc.roomDatabase.UserRemoteMediator
import com.example.homeworkstbc.utils.NetworkUtil
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideHttpLoggingInterceptor ( ) : HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return httpLoggingInterceptor
    }

    @Provides
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logging)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client : OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }



    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providePager (database: AppDatabase,userService: UserService,networkUtil: NetworkUtil)
    : Pager<Int, UserDb> {
        val isNetworkAvailable: () -> Boolean = networkUtil::isNetworkAvailable

        return Pager(
                config = PagingConfig(
                    pageSize = 6,
                    enablePlaceholders = false,
                    prefetchDistance = 1
                ),
        remoteMediator = UserRemoteMediator(
            database = database,
            service = userService,
            isNetworkAvailable = isNetworkAvailable
        ),
        pagingSourceFactory = { database.userDao().getPagingSource() }
        )
    }





}