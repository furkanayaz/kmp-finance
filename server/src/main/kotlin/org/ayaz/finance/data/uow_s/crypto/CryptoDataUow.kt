package org.ayaz.finance.data.uow_s.crypto

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import org.ayaz.finance.data.dto_s.crypto.CryptoListingsResDTO
import org.ayaz.finance.data.dto_s.crypto.CryptoMapResDTO
import org.ayaz.finance.data.dto_s.crypto.CryptoResDTO
import org.ayaz.finance.domain.util.Resource

interface ICryptoDataUow {
    suspend fun getData(limit: Int, start: Int): Resource<List<CryptoMapResDTO>>
    suspend fun getDetailData(limit: Int, start: Int, convert: String): Resource<List<CryptoListingsResDTO>>
}

class CryptoDataUow(
    private val client: HttpClient
) : ICryptoDataUow {
    override suspend fun getData(limit: Int, start: Int): Resource<List<CryptoMapResDTO>> {
        return try {
            val response = client.get("/map") {
                headers {
                    append("limit", limit.toString())
                    append("start", (start + 1).toString())
                }
            }.body<CryptoResDTO<CryptoMapResDTO>>()

            if (response.isSuccess()) Resource.Success(response.data) else Resource.Error(response.status.errorCode, listOf(response.status.errorMessage))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(HttpStatusCode.InternalServerError.value, listOf(e.message.orEmpty()))
        }
    }

    override suspend fun getDetailData(limit: Int, start: Int, convert: String): Resource<List<CryptoListingsResDTO>> {
        return try {
            val response = client.get("/listings/latest") {
                headers {
                    append("limit", limit.toString())
                    append("start", (start + 1).toString())
                }
            }.body<CryptoResDTO<CryptoListingsResDTO>>()

            if (response.isSuccess()) Resource.Success(response.data) else Resource.Error(response.status.errorCode, listOf(response.status.errorMessage))
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(HttpStatusCode.InternalServerError.value, listOf(e.message.orEmpty()))
        }
    }

}