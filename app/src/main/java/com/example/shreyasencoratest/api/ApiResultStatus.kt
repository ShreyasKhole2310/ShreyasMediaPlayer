package com.example.shreyasencoratest.api

data class ApiResultStatus<out T>(var status: Status, val data: T?, var message: String? = "") {
    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> success(data: T): ApiResultStatus<T> {
            return ApiResultStatus(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): ApiResultStatus<T> {
            return ApiResultStatus(Status.ERROR, data, message)
        }

    }
}