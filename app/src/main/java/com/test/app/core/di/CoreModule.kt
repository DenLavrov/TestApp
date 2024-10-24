package com.test.app.core.di

import android.content.Context
import android.content.SharedPreferences
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.test.app.core.data.Dispatchers
import com.test.app.core.data.interceptors.AuthInterceptor
import com.test.app.core.data.interceptors.ConnectionInterceptor
import com.test.app.core.data.refresh.RefreshApi
import com.test.app.core.data.refresh.UnauthorizedHandler
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}

const val BASE_URL = "https://plannerok.ru"

@Module(includes = [CoreModule.Bindings::class])
class CoreModule {

    @Provides
    @CoreScope
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("shared_preferences", 0)

    @Provides
    @CoreScope
    @LoggingInterceptorQualifier
    fun provideLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    @CoreScope
    fun provideHttpClient(
        @AuthInterceptorQualifier authInterceptor: Interceptor,
        @LoggingInterceptorQualifier loggingInterceptor: Interceptor,
        @ConnectionInterceptorQualifier connectionInterceptor: Interceptor,
        authenticator: Authenticator
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(connectionInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authenticator)
            .build()

    @Provides
    @CoreScope
    fun provideDispatchers() = Dispatchers()

    @Provides
    @CoreScope
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl("$BASE_URL/api/v1/users/")
            .build()

    @CoreScope
    @Provides
    fun provideRefreshApi(retrofit: Retrofit): RefreshApi = retrofit.create(RefreshApi::class.java)

    @Module
    interface Bindings {
        @Binds
        @CoreScope
        @AuthInterceptorQualifier
        fun bindAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor

        @Binds
        @CoreScope
        @ConnectionInterceptorQualifier
        fun bindConnectionInterceptor(connectionInterceptor: ConnectionInterceptor): Interceptor

        @Binds
        @CoreScope
        fun bindAuthenticator(authenticator: UnauthorizedHandler): Authenticator
    }
}