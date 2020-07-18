package bohdan.varchenko.data.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

internal val objectMapper: ObjectMapper by lazy {
    ObjectMapper().registerModule(KotlinModule())
}

internal inline fun <reified T> String.fromJson(): T {
    return objectMapper.readValue(this, T::class.java)
}

internal fun Any.toJsonString(): String {
    return objectMapper.writeValueAsString(this)
}