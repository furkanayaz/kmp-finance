package org.ayaz.exchange.domain.use_cases.crypto

import org.ayaz.exchange.data.repositories.crypto.ICryptoDataRepo

class CryptoQuotesLatestUseCase(
    private val repo: ICryptoDataRepo
) {
    suspend operator fun invoke(id: Int, convert: String) = repo.getQuotesLatest(id, convert)
}