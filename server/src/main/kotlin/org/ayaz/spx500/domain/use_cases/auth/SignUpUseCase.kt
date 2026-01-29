package org.ayaz.spx500.domain.use_cases.auth

import org.ayaz.spx500.data.dto_s.auth.SignUpReqDTO
import org.ayaz.spx500.data.repositories.auth.SignUpRepo
import org.koin.core.annotation.Single

@Single
class SignUpUseCase(
    private val signUpRepo: SignUpRepo
) {
    operator fun invoke(req: SignUpReqDTO) = signUpRepo.signUp(req)
}