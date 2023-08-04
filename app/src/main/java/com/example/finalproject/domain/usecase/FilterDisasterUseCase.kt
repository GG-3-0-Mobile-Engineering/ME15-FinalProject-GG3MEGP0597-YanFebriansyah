package com.example.finalproject.domain.usecase

import android.util.Log
import com.example.finalproject.data.model.getGeometriesAsBencanaProperties
import com.example.finalproject.domain.repository.DisasterRepository
import com.example.finalproject.presentation.model.Bencana
import javax.inject.Inject

class FilterDisasterUseCase @Inject constructor(
    private val repository: DisasterRepository
) {
    suspend fun invoke(periode: String, filter: String): MutableList<Bencana> {
        val disasterData = repository.getDisaster(periode)
        val listOfBencana = mutableListOf<Bencana>()
        try {
            if (disasterData.isSuccessful) {
                disasterData.body().apply {
                    val dataSementara =
                        this?.getGeometriesAsBencanaProperties() as MutableList<Bencana>
                    if (dataSementara.isNotEmpty()) {
                        for (i in dataSementara) {
                            if (i.codeArea == filter) {
                                listOfBencana.add(i)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("filter", e.message.toString())
        }
        return listOfBencana
    }
}