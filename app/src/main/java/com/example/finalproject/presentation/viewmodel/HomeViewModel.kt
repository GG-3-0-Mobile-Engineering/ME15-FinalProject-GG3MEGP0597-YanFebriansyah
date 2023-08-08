package com.example.finalproject.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.domain.usecase.FilterDisasterUseCase
import com.example.finalproject.domain.usecase.GetDisasterUseCase
import com.example.finalproject.domain.usecase.NotificationUseCase
import com.example.finalproject.domain.usecase.SearchDisasterUseCase
import com.example.finalproject.presentation.model.Bencana
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCaseGet: GetDisasterUseCase,
    private val filterDisasterUseCase: FilterDisasterUseCase,
    private val searchDisasterUseCase: SearchDisasterUseCase,
    private val notificationUseCase: NotificationUseCase,
) : ViewModel() {

    private val _filteredBencanaList = MutableStateFlow<List<Bencana>>(emptyList())
    val filteredBencanaList: StateFlow<List<Bencana>> = _filteredBencanaList


    private val getDisaster = MutableStateFlow<List<Bencana>>(emptyList())
    val _getDisaster: StateFlow<List<Bencana>> = getDisaster

    private val uiData = MutableLiveData<HomeState>(HomeState(isLoading = true))
    val getData: LiveData<HomeState>
        get() = uiData

    init {
//        set default 2 minggu
        val periode = "1209600"
        getData(periode)
    }

    fun getData(periode: String) {
        viewModelScope.launch {
            try {
                uiData.value = HomeState(isLoading = true)
                val response = useCaseGet.invoke(periode).collect {
                    uiData.value = HomeState(it, isLoading = false)
                }
            } catch (e: Exception) {
                uiData.value = HomeState(error(e.message.toString()), isLoading = true)
            }
        }
    }

    fun searchData(keyword: String, allDisaster: List<Bencana>): Flow<List<Bencana>> {
        return flow {
            val result = searchDisasterUseCase.invoke(allDisaster, keyword)
            emit(result)
        }
    }

    fun checkNotification(context: Context, bencana: List<Bencana>) {
        viewModelScope.launch {
            notificationUseCase.checkAndShowFloodAlertNotification(context, bencana)
        }
    }

    fun filterTypeDisaster(bencana: List<Bencana>, type: String) {
        viewModelScope.launch {
            val result = filterDisasterUseCase.invoke(bencana, type).collect {
                _filteredBencanaList.value = it
            }
        }
    }
}
