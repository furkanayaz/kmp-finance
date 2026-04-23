package org.ayaz.exchange.data.dto_s.crypto

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.ayaz.exchange.data.util.serializers.CryptoDecimal
import org.ayaz.exchange.domain.base.Resource

open class BaseCryptoResDTO<E: Any> {
    open val data: E? = null
    open val status: CryptoResStatusDTO? = null

    fun getResource(): Resource<E> {
        return when {
            status?.isSuccess() == false -> Resource.Error(HttpStatusCode.InternalServerError.value, listOf(status?.getErrorMessage().orEmpty()))
            data == null -> Resource.Error(HttpStatusCode.InternalServerError.value, listOf(status?.getErrorMessage().orEmpty()))
            else -> Resource.Success(data!!)
        }
    }
}

@Serializable
data class CryptoListResDTO<T : Any>(
    override val data: List<T>,
    override val status: CryptoResStatusDTO
): BaseCryptoResDTO<List<T>>()

@Serializable
data class CryptoFilterResDTO<T : Any>(
    override val data: Map<String, T>,
    override val status: CryptoResStatusDTO
): BaseCryptoResDTO<Map<String, T>>()

@Serializable
data class CryptoResStatusDTO(
    @SerialName("error_code") val errorCode: Int,
    @SerialName("error_message") private val errorMessage: String? = null
) {
    fun isSuccess() = errorCode == 0
    fun getErrorMessage(): String = errorMessage ?: "Crypto - empty error message."
}

@Serializable
data class CryptoMapResDTO(
    val id: Int? = null,
    val name: String? = null,
    val symbol: String? = null,
    val slug: String? = null,
    var logoLink: String? = null
)

@Serializable
data class CryptoLogoResDTO(
    val logo: String?
)

@Serializable
data class CryptoQuotesResDTO(
    @SerialName("infinite_supply") val infiniteSupply: Boolean?,
    @SerialName("circulating_supply") val circulatingSupply: CryptoDecimal,
    @SerialName("total_supply") val totalSupply: CryptoDecimal,
    @SerialName("max_supply") val maxSupply: CryptoDecimal,
    @SerialName("date_added") val addedDate: String?,
    @SerialName("last_updated") val lastUpdate: String?,
    val quote: Map<String, QuoteResDTO>?
)

@Serializable
data class QuoteResDTO(
    val price: CryptoDecimal,
    @SerialName("volume_24h") val volume24H: CryptoDecimal,
    @SerialName("percent_change_1h") val percentChange1H: CryptoDecimal,
    @SerialName("percent_change_24h") val percentChange24H: CryptoDecimal,
    @SerialName("percent_change_7d") val percentChange7D: CryptoDecimal,
    @SerialName("percent_change_30d") val percentChange30D: CryptoDecimal,
    @SerialName("percent_change_60d") val percentChange60D: CryptoDecimal,
    @SerialName("percent_change_90d") val percentChange90D: CryptoDecimal,
    @SerialName("market_cap") val marketCap: CryptoDecimal,
    @SerialName("last_updated") val lastUpdate: String?
)