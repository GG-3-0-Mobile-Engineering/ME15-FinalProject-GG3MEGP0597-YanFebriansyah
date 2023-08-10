package com.example.finalproject.domain.usecase

import com.example.finalproject.data.fakeRepository.FakeDisasterRepositoy
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetDisasterUseCaseTest {

    private lateinit var getDisasterUseCase: GetDisasterUseCase
    private lateinit var fakeDisasterRepositoy: FakeDisasterRepositoy

    @Before
    fun setUp() {
        fakeDisasterRepositoy = FakeDisasterRepositoy()
        getDisasterUseCase = GetDisasterUseCase(fakeDisasterRepositoy)
    }

    @Test
    fun `Get Disaster List correct `(): Unit = runBlocking {
        val disasterList = getDisasterUseCase.invoke("").first()
        assertThat((disasterList[0].title.equals("flood"))).isTrue()
    }


    @Test
    fun `Get Disaster List Incorrect `(): Unit = runBlocking {
        val disasterList = getDisasterUseCase.invoke("").first()
        assertThat((disasterList[0].title.equals("earthquake"))).isFalse()
    }


}