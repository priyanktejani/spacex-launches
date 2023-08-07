package com.spacex.launches.core.common.util

/**
 * Sealed class representing a resource state containing data or an error message.
 *
 * @param data The data of type T that the resource holds.
 * @param message An optional error message when the resource represents an error state.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>: Resource<T>(null)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
    class Success<T>(data: T?): Resource<T>(data)
}
