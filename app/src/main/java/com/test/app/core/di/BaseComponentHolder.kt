package com.test.app.core.di

abstract class BaseComponentHolder<Component, Dependencies> {
    protected var component: Component? = null

    abstract fun init(dependencies: Dependencies?): Component

    fun get() = component!!

    open fun clear() {
        component = null
    }
}