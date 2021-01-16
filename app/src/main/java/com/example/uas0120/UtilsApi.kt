package com.example.uas0120

object UtilsApi {
    val apiService: ApiService
        get() = RetrofitClient.getClient(ApiConfig.END_POINT).create(ApiService::class.java)

}