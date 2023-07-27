package com.example.finalproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.repository.DisasterRepository
import com.example.finalproject.domain.usecase.GetDisasterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetDisasterUseCase,
    private val repository: DisasterRepository
) : ViewModel() {

    private val uiData = MutableLiveData<HomeState>(HomeState(isLoading = true))
    val getData: LiveData<HomeState>
        get() = uiData

    init {
        getData()
    }


    fun getData() {
        viewModelScope.launch {
            try {
                uiData.value = HomeState(isLoading = true)
                val response = repository.getDisaster()
                if (response.isSuccessful) {
                    uiData.value = HomeState(disasterData = response.body(), isLoading = false)
                }
            } catch (e: Exception) {
                uiData.value = HomeState(error(e.message.toString()), isLoading = true)
            }
        }
    }
}