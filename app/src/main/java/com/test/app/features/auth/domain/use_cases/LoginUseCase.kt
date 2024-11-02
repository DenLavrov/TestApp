package com.test.app.features.auth.domain.use_cases

import com.test.app.features.auth.di.AuthScope
import com.test.app.features.auth.domain.exceptions.ValidationException
import com.test.app.features.auth.domain.repository.IAuthRepository
import com.test.app.features.auth.domain.validation.AuthValidator
import javax.inject.Inject

@AuthScope
class LoginUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val authValidator: AuthValidator
) {
    suspend operator fun invoke(phone: String, code: String) =
        if (authValidator.validateCode(code).not()) {
            throw ValidationException()
        } else {
            authRepository.login(phone, code)
        }
}