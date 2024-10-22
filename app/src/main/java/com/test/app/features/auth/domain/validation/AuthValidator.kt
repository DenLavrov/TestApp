package com.test.app.features.auth.domain.validation

import com.arpitkatiyarprojects.countrypicker.utils.CountryPickerUtils
import com.test.app.features.auth.di.AuthScope
import javax.inject.Inject

@AuthScope
class AuthValidator @Inject constructor() {
    private val userNameSymbols by lazy {
        ('a'..'z') + ('0'..'9') + '_' + '-'
    }

    fun validatePhone(phone: String) = CountryPickerUtils.isMobileNumberValid(phone)

    fun validateCode(code: String) = code.length == 6

    fun validateName(name: String) = name.isNotEmpty()

    fun validateUserName(userName: String) = userName.isNotEmpty() && userName.lowercase().all {
        it in userNameSymbols
    }
}