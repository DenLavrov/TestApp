package com.test.app.features.auth.domain.use_cases

import com.test.app.features.auth.di.AuthScope
import com.test.app.features.auth.domain.models.ValidationError
import com.test.app.features.auth.domain.repository.IAuthRepository
import com.test.app.features.auth.domain.validation.AuthValidator
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@AuthScope
class SendCodeUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val authValidator: AuthValidator
) {
    operator fun invoke(phone: String) = authRepository.sendCode(phone).map {
        if (it.not())
            throw Error()
        phone
    }.onStart {
        if (authValidator.validatePhone(phone).not())
            throw ValidationError()
    }
}