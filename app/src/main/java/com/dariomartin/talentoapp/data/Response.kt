package com.dariomartin.talentoapp.data

sealed class Response<T>(data: T? = null, message: String? = null) {
    class Success<T>(data: T) : Response<T>(data = data)
    class Error<T>(message: String?) : Response<T>(message = message)
}
