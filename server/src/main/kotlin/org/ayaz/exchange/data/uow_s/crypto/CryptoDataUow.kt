package org.ayaz.exchange.data.uow_s.crypto

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.util.appendAll
import org.ayaz.exchange.data.dto_s.crypto.CryptoListResDTO
import org.ayaz.exchange.data.dto_s.crypto.CryptoQuotesResDTO
import org.ayaz.exchange.data.dto_s.crypto.CryptoMapResDTO
import org.ayaz.exchange.data.dto_s.crypto.CryptoFilterResDTO
import org.ayaz.exchange.data.dto_s.crypto.CryptoLogoResDTO
import org.ayaz.exchange.domain.base.Resource

interface ICryptoDataUow {
    suspend fun getMap(limit: Int, start: Int): Resource<List<CryptoMapResDTO>>
    suspend fun getInfo(id: Int): Resource<Map<String, CryptoLogoResDTO>>
    suspend fun getQuotesLatest(id: Int, convert: String): Resource<Map<String, CryptoQuotesResDTO>>
}

class CryptoDataUow(
    private val client: HttpClient
) : ICryptoDataUow {
    private companion object {
        const val KEY_ID = "id"
        const val KEY_LIMIT = "limit"
        const val KEY_START = "start"
        const val KEY_CONVERT = "convert"
    }

    override suspend fun getMap(limit: Int, start: Int): Resource<List<CryptoMapResDTO>> {
        try {
            val response = client.get(CryptoDataEndpoints.MAP_ENDPOINT) {
                url.parameters.appendAll(mapOf(KEY_LIMIT to limit.toString(), KEY_START to (start * 2 + 1).toString()))
            }.body<CryptoListResDTO<CryptoMapResDTO>>()

            return response.getResource()
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(HttpStatusCode.InternalServerError.value, listOf(e.message.orEmpty()))
        }
    }

    override suspend fun getInfo(id: Int): Resource<Map<String, CryptoLogoResDTO>> {
        try {
            val response = client.get(CryptoDataEndpoints.INFO_ENDPOINT) {
                url.parameters.append(KEY_ID, id.toString())
            }.body<CryptoFilterResDTO<CryptoLogoResDTO>>()

            return response.getResource()
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(HttpStatusCode.InternalServerError.value, listOf(e.message.orEmpty()))
        }
    }

    override suspend fun getQuotesLatest(id: Int, convert: String): Resource<Map<String, CryptoQuotesResDTO>> {
        try {
            val response = client.get(CryptoDataEndpoints.QUOTES_LATEST_ENDPOINT) {
                url.parameters.appendAll(mapOf(KEY_ID to id.toString(), KEY_CONVERT to convert))
            }.body<CryptoFilterResDTO<CryptoQuotesResDTO>>()

            return response.getResource()
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(HttpStatusCode.InternalServerError.value, listOf(e.message.orEmpty()))
        }
    }

}