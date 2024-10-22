package com.test.app.features.auth.domain.use_cases

import com.test.app.features.auth.di.AuthScope
import com.test.app.features.auth.domain.models.NameValidationError
import com.test.app.features.auth.domain.models.UserNameValidationError
import com.test.app.features.auth.domain.repository.IAuthRepository
import com.test.app.features.auth.domain.validation.AuthValidator
import javax.inject.Inject

@AuthScope
class RegisterUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val validator: AuthValidator
) {
    suspend operator fun invoke(phone: String, userName: String, name: String) =
        if (validator.validateUserName(userName).not()) {
            throw UserNameValidationError()
        } else if (validator.validateName(name).not()) {
            throw NameValidationError()
        } else {
            authRepository.register(phone, userName, name)
        }
}