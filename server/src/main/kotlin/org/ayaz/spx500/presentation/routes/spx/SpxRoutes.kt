package org.ayaz.spx500.presentation.routes.spx

import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.ayaz.spx500.data.util.Response
import org.ayaz.spx500.domain.use_cases.spx.GetSpxDataDetailUseCase
import org.ayaz.spx500.domain.use_cases.spx.GetSpxDataUseCase
import org.ayaz.spx500.presentation.util.CallUtil.sendResponse
import org.koin.ktor.ext.inject

fun Route.spxRoutes() {
    authenticate {
        get(SpxEndpoints.GET_SPX_DATA) {
            val getSpxDataUseCase by inject<GetSpxDataUseCase>()

            val pageNo = call.request.queryParameters["pageNo"]?.toInt()
            val pageSize = call.request.queryParameters["pageSize"]?.toInt() ?: 10

            if (pageNo == null) call.sendResponse(Response.Error(errorMessages = listOf("pageno.required")))

            call.sendResponse(getSpxDataUseCase(pageNo!!, pageSize))
        }

        get(SpxEndpoints.GET_SPX_DATA_DETAIL) {
            val getSpxDataDetailUseCase by inject<GetSpxDataDetailUseCase>()
        }
    }
}