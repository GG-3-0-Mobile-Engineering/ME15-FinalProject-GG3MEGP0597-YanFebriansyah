package com.example.finalproject.domain.repository

import com.example.finalproject.data.model.ResponseData
import retrofit2.Response

interface DisasterRepository {
    suspend fun getDisaster(): Response<ResponseData>

}