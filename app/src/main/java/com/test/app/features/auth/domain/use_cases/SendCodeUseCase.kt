package com.test.app.features.auth.domain.use_cases

import com.test.app.features.auth.di.AuthScope
import com.test.app.features.auth.domain.exceptions.ValidationException
import com.test.app.features.auth.domain.repository.IAuthRepository
import com.test.app.features.auth.domain.validation.AuthValidator
import javax.inject.Inject

@AuthScope
class SendCodeUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val authValidator: AuthValidator
) {
    suspend operator fun invoke(phone: String) = if (authValidator.validatePhone(phone).not()) {
        throw ValidationException()
    } else {
        authRepository.sendCode(phone).let {
            if (it.not())
                throw Throwable("Code sending failed")
            phone
        }
    }
}