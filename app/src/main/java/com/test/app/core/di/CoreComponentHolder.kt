package com.test.app.core.di

import android.content.Context

object CoreComponentHolder : BaseComponentHolder<CoreComponent, Context>() {

    override fun init(dependencies: Context?): CoreComponent {
        if (component == null)
            component = DaggerCoreComponent.builder().context(dependencies!!).build()
        return get()
    }
}