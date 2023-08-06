package com.example.finalproject.data.repository

import com.example.finalproject.data.ApiService
import com.example.finalproject.data.model.ResponseData
import com.example.finalproject.data.model.Result
import com.example.finalproject.domain.repository.DisasterRepository
import com.example.finalproject.presentation.model.Bencana
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class DisasterRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DisasterRepository {
    override suspend fun getDisaster(periode: String): Response<ResponseData> {
        return apiService.getReport(periode)
    }

    override suspend fun filterDisaster(data: List<Bencana>): List<Bencana> {
        return data
    }

}
