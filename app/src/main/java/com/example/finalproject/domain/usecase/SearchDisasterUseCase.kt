package com.example.finalproject.domain.usecase

import com.example.finalproject.presentation.model.Bencana
import com.example.finalproject.utils.Constants
import javax.inject.Inject

class SearchDisasterUseCase @Inject constructor(){
    suspend operator fun invoke(list: List<Bencana>, keyword: String): List<Bencana> {
        if (keyword.isBlank()) {
            return list
        } else {
            val searchLowerCase = keyword.toLowerCase()
            val codeArea = Constants.provinceMap.entries.find { entry ->
                entry.key.toLowerCase() == searchLowerCase
            }?.value
            val filteredList = if (codeArea != null) {
                list.filter { bencana ->
                    bencana.codeArea.equals(codeArea, ignoreCase = true)
                }
            } else {
                list.filter { bencana ->
                    bencana.title!!.toLowerCase().contains(searchLowerCase)
                }
            }
            return filteredList
        }
    }
}