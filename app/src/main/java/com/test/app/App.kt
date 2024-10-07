package com.test.app

import android.app.Application
import com.test.app.core.di.CoreComponentHolder
import com.test.app.main.presentation.di.AppComponentHolder

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppComponentHolder.init(CoreComponentHolder.init(applicationContext))
    }
}