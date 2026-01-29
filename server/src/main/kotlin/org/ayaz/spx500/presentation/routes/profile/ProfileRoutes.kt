package org.ayaz.spx500.presentation.routes.profile

import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.ayaz.spx500.presentation.util.CallUtil.getSingleFile

fun Route.profileRoutes() {
    post(ProfileEndpoints.ADD_IMAGE) {
        val imageFile = call.getSingleFile()
    }
}