package com.example.finalproject.data.repository

import com.example.finalproject.data.ApiService
import com.example.finalproject.data.model.ResponseData
import com.example.finalproject.data.model.Result
import com.example.finalproject.domain.repository.DisasterRepository
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class DisasterRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DisasterRepository {
    override suspend fun getDisaster(): Response<ResponseData> {
        return apiService.getReport()
    }
}