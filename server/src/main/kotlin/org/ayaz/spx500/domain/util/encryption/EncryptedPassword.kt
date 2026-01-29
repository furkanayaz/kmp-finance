package org.ayaz.spx500.domain.util.encryption

data class EncryptedPassword(
    val saltValue: String,
    val encodedPassword: String
)
