package org.ayaz.finance.data.repositories.crypto

import org.ayaz.finance.data.dto_s.crypto.CryptoListingsResDTO
import org.ayaz.finance.data.dto_s.crypto.CryptoMapResDTO
import org.ayaz.finance.data.uow_s.crypto.ICryptoDataUow
import org.ayaz.finance.data.util.Response
import org.ayaz.finance.domain.util.Resource

interface ICryptoDataRepo {
    suspend fun getData(limit: Int, start: Int): Response<List<CryptoMapResDTO>>
    suspend fun getDetailData(limit: Int, start: Int, convert: String): Response<List<CryptoListingsResDTO>>
}

class CryptoDataRepo(
    private val cryptoDataUow: ICryptoDataUow
): ICryptoDataRepo {
    override suspend fun getData(limit: Int, start: Int): Response<List<CryptoMapResDTO>> {
        return when(val response = cryptoDataUow.getData(limit, start)) {
            is Resource.Error<List<CryptoMapResDTO>> -> Response.Error(code = response.code, errorMessages = response.messages)
            is Resource.Success<List<CryptoMapResDTO>> -> Response.Success(item = response.item)
        }
    }

    override suspend fun getDetailData(
        limit: Int,
        start: Int,
        convert: String
    ): Response<List<CryptoListingsResDTO>> {
        return when(val response = cryptoDataUow.getDetailData(limit, start, convert)) {
            is Resource.Error<List<CryptoListingsResDTO>> -> Response.Error(code = response.code, errorMessages = response.messages)
            is Resource.Success<List<CryptoListingsResDTO>> -> Response.Success(item = response.item)
        }
    }

}