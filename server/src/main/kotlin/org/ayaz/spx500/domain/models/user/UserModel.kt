package org.ayaz.spx500.domain.models.user

import kotlin.time.Instant

data class UserModel(
    val uuid: String,
    val fistName: String,
    val lastName: String,
    val email: String,
    val createdAt: Instant
)
