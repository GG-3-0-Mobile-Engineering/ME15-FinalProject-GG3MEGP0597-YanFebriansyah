package com.example.finalproject.domain.usecase

import com.example.finalproject.data.model.getGeometriesAsBencanaProperties
import com.example.finalproject.domain.repository.DisasterRepository
import com.example.finalproject.presentation.model.Bencana
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject





class GetDisasterUseCase @Inject constructor(
    private val repository: DisasterRepository
) {
    operator fun invoke(periode: String): Flow<List<Bencana>> {
        return flow {
            val response = repository.getDisaster(periode)
            if (response.isSuccessful) {
                val responseData = response.body()
                val dataSementara = responseData?.getGeometriesAsBencanaProperties()
                emit(dataSementara!!)
            }
        }
    }
}