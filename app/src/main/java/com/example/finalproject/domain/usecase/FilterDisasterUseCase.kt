package com.example.finalproject.domain.usecase

import android.util.Log
import com.example.finalproject.data.model.getGeometriesAsBencanaProperties
import com.example.finalproject.domain.repository.DisasterRepository
import com.example.finalproject.presentation.model.Bencana
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FilterDisasterUseCase @Inject constructor(
    private val repository: DisasterRepository
) {

    operator fun invoke(bencana: List<Bencana>, type: String): Flow<List<Bencana>> {
        return flow {
            val listOfBencana = mutableListOf<Bencana>()
            if (bencana.isNotEmpty()) {
                val filteredList = bencana.filter {
                    if (type == "all") {
                        true
                    } else {
                        it.title == type
                    }
                }
                emit(filteredList)
            }
        }
    }
}