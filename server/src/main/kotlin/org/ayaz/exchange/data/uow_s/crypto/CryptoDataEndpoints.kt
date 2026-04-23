package org.ayaz.exchange.data.uow_s.crypto

object CryptoDataEndpoints {
    private const val BASE_URL = "/v1/cryptocurrency"
    const val MAP_ENDPOINT = "$BASE_URL/map"
    const val INFO_ENDPOINT = "$BASE_URL/info"
    const val QUOTES_LATEST_ENDPOINT = "$BASE_URL/quotes/latest"
}