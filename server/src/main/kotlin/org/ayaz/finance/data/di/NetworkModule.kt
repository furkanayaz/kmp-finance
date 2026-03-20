package org.ayaz.finance.data.di

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import org.ayaz.finance.data.util.CoinMarketCap
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class NetworkModule {

    @Single
    @CoinMarketCap
    fun provideHTTPClient(): HttpClient = HttpClient(CIO) {
        defaultRequest {
            url("https://pro-api.coinmarketcap.com/v1/cryptocurrency")
            headers {
                append("X-CMC_PRO_API_KEY", "6f5321fd-2927-4386-87b2-a1da537b4b8f")
                append(HttpHeaders.Accept, ContentType.Application.Json.contentType)
            }
        }
    }

}