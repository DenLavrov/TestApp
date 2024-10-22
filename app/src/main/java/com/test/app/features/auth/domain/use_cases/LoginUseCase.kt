package com.test.app.features.auth.domain.use_cases

import com.test.app.features.auth.di.AuthScope
import com.test.app.features.auth.domain.models.ValidationError
import com.test.app.features.auth.domain.repository.IAuthRepository
import com.test.app.features.auth.domain.validation.AuthValidator
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@AuthScope
class LoginUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val authValidator: AuthValidator
) {
    operator fun invoke(phone: String, code: String) = authRepository.login(phone, code)
        .map { it.isUserExists ?: false }
        .onStart {
            if (authValidator.validateCode(code).not())
                throw ValidationError()
        }
}