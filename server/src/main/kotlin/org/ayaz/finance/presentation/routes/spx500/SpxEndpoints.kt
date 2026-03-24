package org.ayaz.finance.presentation.routes.spx500

object SpxEndpoints {
    private const val BASE_URL = "spx"
    const val GET_DATA = "$BASE_URL/get-data"
    const val GET_DATA_DETAIL = "$BASE_URL/get-data-detail/{symbol}"
}