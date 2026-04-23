package org.ayaz.exchange.domain.use_cases.crypto

import org.ayaz.exchange.data.repositories.crypto.ICryptoDataRepo

class CryptoMapUseCase(
    private val repo: ICryptoDataRepo
) {
    suspend operator fun invoke(limit: Int, start: Int) = repo.getMap(limit, start)
}