package com.otakenne.devicehealthsdk.data.utility

import java.util.*

private const val DEFAULT_STRING = ""
private const val DEFAULT_INT = Int.MIN_VALUE
private const val DEFAULT_LONG = Long.MIN_VALUE
internal val DEFAULT_DATE = now()

val String.Companion.DEFAULT: String
    get() = DEFAULT_STRING

val Int.Companion.DEFAULT: Int
    get() = DEFAULT_INT

val Long.Companion.DEFAULT: Long
    get() = DEFAULT_LONG

internal fun now() = Date()

fun doNothing() = Unit