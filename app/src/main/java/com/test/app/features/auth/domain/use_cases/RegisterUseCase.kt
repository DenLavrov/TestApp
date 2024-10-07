package com.test.app.features.auth.domain.use_cases

import com.test.app.core.data.Dispatchers
import com.test.app.features.auth.domain.models.NameValidationError
import com.test.app.features.auth.domain.models.UserNameValidationError
import com.test.app.features.auth.presentation.di.AuthScope
import com.test.app.features.auth.domain.repository.IAuthRepository
import com.test.app.features.auth.domain.validation.AuthValidator
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AuthScope
class RegisterUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val dispatchers: Dispatchers,
    private val validator: AuthValidator
) {

    operator fun invoke(phone: String, userName: String, name: String) =
        if (validator.validateUserName(userName).not()) {
            flow { throw UserNameValidationError() }
        } else if (validator.validateName(name).not()) {
            flow { throw NameValidationError() }
        } else {
            authRepository.register(phone, userName, name)
                .map { it.isUserExists }
                .flowOn(dispatchers.io)
        }
}