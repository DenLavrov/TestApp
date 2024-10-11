package com.test.app.core.di

import javax.inject.Qualifier

@Qualifier
annotation class LoggingInterceptorQualifier

@Qualifier
annotation class AuthInterceptorQualifier

@Qualifier
annotation class ConnectionInterceptorQualifier
