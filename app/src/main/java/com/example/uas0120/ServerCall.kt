package com.example.uas0120

interface ServerCall {
    fun onSuccess(response: String)

    fun onFailed(response: String)

    fun onFailure(throwable: Throwable)
}