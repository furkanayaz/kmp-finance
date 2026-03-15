package org.ayaz.spx500.domain.util

fun interface Mapper<in T: Any, out S: Any> {
    operator fun invoke(dto: T): S
}