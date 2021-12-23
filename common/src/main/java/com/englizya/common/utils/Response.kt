package com.englizya.common.utils

data class Response<out T>(val status: Status, val data: T?, val error: Exception?) {

    companion object {

        fun <T> success(data: T?): Response<T> {
            return Response(Status.SUCCESS, data, null)
        }

        fun <T> error(error: Exception?, data: T?): Response<T> {
            return Response(Status.ERROR, data, error)
        }

        fun <T> error(error: Throwable?, data: T?): Response<T> {
            return Response(Status.ERROR, data, Exception(error))
        }

        fun <T> loading(data: T?): Response<T> {
            return Response(Status.LOADING, data, null)
        }
    }
}