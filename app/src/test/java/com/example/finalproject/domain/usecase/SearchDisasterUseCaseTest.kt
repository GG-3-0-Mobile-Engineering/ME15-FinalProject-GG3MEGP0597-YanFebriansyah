package com.example.finalproject.domain.usecase

import com.example.finalproject.data.fakeRepository.FakeBencana
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchDisasterUseCaseTest {

    private lateinit var searchDisasterUseCase: SearchDisasterUseCase

    @Before
    fun setUp() {
        searchDisasterUseCase = SearchDisasterUseCase()
    }

    @Test
    fun `Check result searching when  keyword is not blank`() = runBlocking {
        val searchList =
            searchDisasterUseCase.invoke(FakeBencana.fakeBencana, "dki jakarta").first()
        assertThat((searchList.codeArea.equals("ID-JK"))).isTrue()
    }

    @Test
    fun `Check result searching when keyword is blank return all bencana`() = runBlocking {
        val searchList = searchDisasterUseCase.invoke(FakeBencana.fakeBencana, " ")
        assertThat(searchList.isEmpty()).isFalse()
    }


}
