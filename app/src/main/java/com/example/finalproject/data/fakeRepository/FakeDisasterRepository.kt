package com.example.finalproject.data.fakeRepository

import com.example.finalproject.data.model.ResponseData
import com.example.finalproject.domain.repository.DisasterRepository
import retrofit2.Response

class FakeDisasterRepositoy() : DisasterRepository {
    override suspend fun getDisaster(periode: String): Response<ResponseData> {
        return Response.success(FakeData.fakeResponseData)
    }
}
