package org.ayaz.exchange.data.repositories.crypto

import org.ayaz.exchange.data.dto_s.crypto.CryptoQuotesResDTO
import org.ayaz.exchange.data.dto_s.crypto.CryptoMapResDTO
import org.ayaz.exchange.data.uow_s.crypto.ICryptoDataUow
import org.ayaz.exchange.data.base.Response
import org.ayaz.exchange.domain.base.Resource

interface ICryptoDataRepo {
    suspend fun getMap(limit: Int, start: Int): Response<List<CryptoMapResDTO>>
    suspend fun getInfo(symbols: List<String>): Map<String, String?>
    suspend fun getQuotesLatest(id: Int, convert: String): Response<Map<String, CryptoQuotesResDTO>>
}

class CryptoDataRepo(
    private val cryptoDataUow: ICryptoDataUow
): ICryptoDataRepo {
    override suspend fun getMap(limit: Int, start: Int): Response<List<CryptoMapResDTO>> {
        return when(val response = cryptoDataUow.getMap(limit, start)) {
            is Resource.Error<List<CryptoMapResDTO>> -> Response.Error(code = response.code, errorMessages = response.messages)
            is Resource.Success<List<CryptoMapResDTO>> -> {
                val symbols = response.item.mapNotNull { it.symbol }
                val logoLinks = getInfo(symbols)

                val itemData = response.item.onEach { cryptoMap ->
                    cryptoMap.symbol?.let { symbol ->
                        cryptoMap.logoLink = logoLinks[symbol]
                    }
                }
                Response.Success(item = itemData)
            }
        }
    }

    override suspend fun getInfo(
        symbols: List<String>
    ): Map<String, String?> = cryptoDataUow.getInfo(symbols)

    override suspend fun getQuotesLatest(
        id: Int,
        convert: String
    ): Response<Map<String, CryptoQuotesResDTO>> {
        return when(val response = cryptoDataUow.getQuotesLatest(id, convert)) {
            is Resource.Error<Map<String, CryptoQuotesResDTO>> -> Response.Error(code = response.code, errorMessages = response.messages)
            is Resource.Success<Map<String, CryptoQuotesResDTO>> -> Response.Success(item = response.item)
        }
    }

}