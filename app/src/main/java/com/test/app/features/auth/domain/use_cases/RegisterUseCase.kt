package com.test.app.features.auth.domain.use_cases

import com.test.app.core.data.Dispatchers
import com.test.app.features.auth.di.AuthScope
import com.test.app.features.auth.domain.models.NameValidationError
import com.test.app.features.auth.domain.models.UserNameValidationError
import com.test.app.features.auth.domain.repository.IAuthRepository
import com.test.app.features.auth.domain.validation.AuthValidator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@AuthScope
class RegisterUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val dispatchers: Dispatchers,
    private val validator: AuthValidator
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(phone: String, userName: String, name: String) = flow {
        if (validator.validateUserName(userName).not())
            throw UserNameValidationError()
        if (validator.validateName(name).not())
            throw NameValidationError()
        emit(Unit)
    }
        .flowOn(dispatchers.default)
        .flatMapConcat {
            authRepository.register(phone, userName, name)
        }
}