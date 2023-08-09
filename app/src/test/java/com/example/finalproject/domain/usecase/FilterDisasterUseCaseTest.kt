package com.example.finalproject.domain.usecase

import com.example.finalproject.data.fakeRepository.FakeDisasterRepositoy
import com.example.finalproject.presentation.model.Bencana
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FilterDisasterUseCaseTest {

    private lateinit var filterDisasterUseCase: FilterDisasterUseCase


    val fakeBencana = listOf<Bencana>(
        Bencana(
            title = "flood",
            description = "flood",
            image = "flood",
            codeArea = "flood",
            latitude = 2.43,
            longitude = 2.43,
            floodDepth = 12,
            time = "2021-01-01"
        ),
        Bencana(
            title = "disaster",
            description = "flood",
            image = "http://image1.com",
            codeArea = "ID-JK",
            latitude = 2.43,
            longitude = 2.43,
            floodDepth = 12,
            time = "2021-01-01"
        ),
        Bencana(
            title = "wize",
            description = "flood",
            image = "flood",
            codeArea = "flood",
            latitude = 2.43,
            longitude = 2.43,
            floodDepth = 12,
            time = "2021-01-01"
        ),
    )

    @Before
    fun setUp() {
        filterDisasterUseCase = FilterDisasterUseCase()
    }

    @Test
    fun `Filter Disaster List correct`(): Unit = runBlocking {
        val filterList = filterDisasterUseCase.invoke(fakeBencana, "flood").first()
        assertThat((filterList[0].title.equals("flood"))).isTrue()
    }


    @Test
    fun `Filter Disaster List uncorrect`(): Unit = runBlocking {
        val filterList = filterDisasterUseCase.invoke(fakeBencana, "wize").first()
        assertThat((filterList[0].title.equals("flood"))).isFalse()
    }


}