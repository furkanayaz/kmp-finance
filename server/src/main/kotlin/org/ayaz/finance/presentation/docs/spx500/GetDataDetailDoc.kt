package org.ayaz.finance.presentation.docs.spx500

import io.ktor.http.HttpStatusCode
import io.ktor.openapi.jsonSchema
import io.ktor.server.routing.Route
import io.ktor.server.routing.openapi.describe
import io.ktor.utils.io.ExperimentalKtorApi
import org.ayaz.finance.data.dto_s.spx.SpxDetailResDTO
import org.ayaz.finance.presentation.docs.DocTags

@OptIn(ExperimentalKtorApi::class)
fun Route.setGetDataDetailDoc() {
    describe {
        tag(DocTags.SPX500_TAG)
        summary = "This endpoint retrieves the list of all S&P 500 companies detail..."
        description = "Retrieves the complete list of S&P 500 companies detail."

        parameters {
            path("symbol") {
                description = "Required to retrieve company detail. Examples: TSLA, AAPL, META etc..."
                required = true
            }
        }

        responses {
            HttpStatusCode.OK {
                description = "Retrieves the specific company detail according to the symbol name..."
                schema = jsonSchema<List<SpxDetailResDTO>>()
            }
        }
    }
}