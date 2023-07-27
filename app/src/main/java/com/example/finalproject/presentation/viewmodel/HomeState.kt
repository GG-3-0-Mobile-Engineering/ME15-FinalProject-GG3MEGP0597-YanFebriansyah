package com.example.finalproject.presentation.viewmodel

import com.example.finalproject.data.model.ResponseData

data class HomeState(
    val disasterData: ResponseData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)