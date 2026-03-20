package org.ayaz.finance.domain.use_cases.crypto

import org.ayaz.finance.data.repositories.crypto.ICryptoDataRepo

class CryptoDataUseCase(
    private val repo: ICryptoDataRepo
) {
    suspend operator fun invoke(limit: Int, start: Int) = repo.getData(limit, start)
}