package com.example.finalproject.domain.usecase

import com.example.finalproject.data.model.getGeometriesAsBencanaProperties
import com.example.finalproject.domain.repository.DisasterRepository
import com.example.finalproject.presentation.model.Bencana


class GetDisasterUseCase(
    private val repository: DisasterRepository
) {
    suspend operator fun invoke(periode: String): List<Bencana> {
        val listOfBencana = mutableListOf<Bencana>()
        val response = repository.getDisaster(periode)
        if (response.isSuccessful) {
            val responseData = response.body()
            if (responseData != null) {
                val dataSementara = responseData.getGeometriesAsBencanaProperties()
                listOfBencana.addAll(dataSementara!!)
            }
        }

        return listOfBencana
    }
}