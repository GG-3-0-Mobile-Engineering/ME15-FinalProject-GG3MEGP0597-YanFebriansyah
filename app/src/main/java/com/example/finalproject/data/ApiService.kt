package com.example.finalproject.data

import com.example.finalproject.data.model.ResponseData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //    @GET("reports?timeperiod=604800")
//    suspend fun getReport(): Response<ResponseData>

    @GET("reports")
    suspend fun getReport(@Query("timeperiod") timeperiod: String): Response<ResponseData>
}