package org.ayaz.spx500.presentation.util

sealed interface MessengerExceptions {
    data class MissingBodyException(override val message: String = "Request body is required."): Exception(), MessengerExceptions
    data class MissingFieldException(override val message: String = "All of the field(s) are required for your request body."): Exception(), MessengerExceptions
}