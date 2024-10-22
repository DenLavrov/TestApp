package com.test.app.features.auth.domain.use_cases

import com.test.app.core.data.Dispatchers
import com.test.app.features.auth.di.AuthScope
import com.test.app.features.auth.domain.models.ValidationError
import com.test.app.features.auth.domain.repository.IAuthRepository
import com.test.app.features.auth.domain.validation.AuthValidator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AuthScope
class SendCodeUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val authValidator: AuthValidator,
    private val dispatchers: Dispatchers
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(phone: String) = flow {
        if (authValidator.validatePhone(phone).not())
            throw ValidationError()
        emit(Unit)
    }
        .flowOn(dispatchers.default)
        .flatMapConcat {
            authRepository.sendCode(phone).map {
                if (it.not())
                    throw Error()
                phone
            }
        }
}