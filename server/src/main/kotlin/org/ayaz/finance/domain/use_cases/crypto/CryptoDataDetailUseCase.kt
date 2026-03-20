package org.ayaz.finance.domain.use_cases.crypto

import org.ayaz.finance.data.repositories.crypto.ICryptoDataRepo

class CryptoDataDetailUseCase(
    private val repo: ICryptoDataRepo
) {
    suspend operator fun invoke(limit: Int, start: Int, convert: String) = repo.getDetailData(limit, start, convert)
}