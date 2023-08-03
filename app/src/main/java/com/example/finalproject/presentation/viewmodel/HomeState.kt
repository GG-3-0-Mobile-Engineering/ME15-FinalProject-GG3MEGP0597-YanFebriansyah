package com.example.finalproject.presentation.viewmodel

import com.example.finalproject.data.model.ResponseData
import com.example.finalproject.presentation.model.Bencana

data class HomeState(
    val disasterData: List<Bencana> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)