package com.test.app.core.data

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class Dispatchers @Inject constructor() {
    val io
        get() = Dispatchers.IO

    val default
        get() = Dispatchers.Default
}