package com.example.finalproject.domain.repository

import com.example.finalproject.data.model.ResponseData
import com.example.finalproject.presentation.model.Bencana
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface DisasterRepository {
    suspend fun getDisaster(periode:String): Response<ResponseData>
}