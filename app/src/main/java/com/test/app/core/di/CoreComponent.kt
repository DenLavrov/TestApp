package com.test.app.core.di

import android.content.Context
import com.test.app.core.data.Storage
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit

@Component(modules = [CoreModule::class])
@CoreScope
interface CoreComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): CoreComponent
    }

    fun provideRetrofit(): Retrofit

    fun provideStorage(): Storage
}