package org.ayaz.spx500.domain.use_cases.auth

import org.ayaz.spx500.data.dto_s.auth.LoginReqDTO
import org.ayaz.spx500.data.repositories.auth.LoginRepo
import org.koin.core.annotation.Single

@Single
class LoginUseCase(
    private val loginRepo: LoginRepo
) {
    suspend operator fun invoke(req: LoginReqDTO) = loginRepo.login(req)
}