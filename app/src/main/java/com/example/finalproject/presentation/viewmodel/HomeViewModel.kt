package com.example.finalproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flow
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.repository.DisasterRepository
import com.example.finalproject.domain.usecase.FilterDisasterUseCase
import com.example.finalproject.domain.usecase.GetDisasterUseCase
import com.example.finalproject.presentation.model.Bencana
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: GetDisasterUseCase,
    private val filterDisasterUseCase: FilterDisasterUseCase,
    private val repository: DisasterRepository
) : ViewModel() {


    private val _filteredBencanaList = MutableStateFlow<List<Bencana>>(emptyList())
    val filteredBencanaList: StateFlow<List<Bencana>> = _filteredBencanaList


    private val uiData = MutableLiveData<HomeState>(HomeState(isLoading = true))
    val getData: LiveData<HomeState>
        get() = uiData

    init {
        val periode = "1209600"
        getData(periode)
    }


    fun getData(periode: String) {
        viewModelScope.launch {
            try {
                uiData.value = HomeState(isLoading = true)
                val response = useCase.invoke(periode)
                uiData.value = HomeState(response, isLoading = false)
            } catch (e: Exception) {
                uiData.value = HomeState(error(e.message.toString()), isLoading = true)
            }
        }
    }

    fun filterData(periode: String, filter: String) {
        viewModelScope.launch {
            try {
//                uiData.value = HomeState(isLoading = true)
                val response = withContext(Dispatchers.IO) {
                    filterDisasterUseCase.invoke(periode, filter)
                }
                _filteredBencanaList.value = response
            } catch (e: Exception) {
//                uiData.value = HomeState(error(e.message.toString()), isLoading = true)
            }
        }
    }
}
