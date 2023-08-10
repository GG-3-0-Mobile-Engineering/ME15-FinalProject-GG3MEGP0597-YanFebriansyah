package com.example.finalproject.domain.usecase

import com.example.finalproject.data.fakeRepository.FakeBencana
import com.example.finalproject.data.fakeRepository.FakeDisasterRepositoy
import com.example.finalproject.presentation.model.Bencana
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FilterDisasterUseCaseTest {

    private lateinit var filterDisasterUseCase: FilterDisasterUseCase


    @Before
    fun setUp() {
        filterDisasterUseCase = FilterDisasterUseCase()
    }

    @Test
    fun `Filter Disaster List correct`(): Unit = runBlocking {
        val filterList = filterDisasterUseCase.invoke(FakeBencana.fakeBencana, "flood").first()
        assertThat((filterList[0].title.equals("flood"))).isTrue()
    }


    @Test
    fun `Filter Disaster List incorrect`(): Unit = runBlocking {
        val filterList = filterDisasterUseCase.invoke(FakeBencana.fakeBencana, "haze").first()
        assertThat((filterList[0].title.equals("flood"))).isFalse()
    }

}