package com.example.finalproject.data.repository

import com.example.finalproject.data.ApiService
import com.example.finalproject.data.fakeRepository.FakeData
import com.example.finalproject.data.model.ResponseData
import com.example.finalproject.domain.repository.DisasterRepository
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class DisasterRepositoryImplTest {


    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var disasterRepository: DisasterRepository


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        disasterRepository = DisasterRepositoryImpl(mockApiService)
    }

    @Test
    fun `test repository getDisaster success`() {
        runBlocking {
            // Mocking the ApiService response
            val mockResponse: Response<ResponseData> = mockResponseWithData()
            `when`(mockApiService.getReport("26135")).thenReturn(mockResponse)

            // Call the repository method
            val result = disasterRepository.getDisaster("26135")

            // Verify the result
            assert(result.isSuccessful)
            assert(result.body() != null)
            assert(result.body()?.result != null)
        }
    }
    private fun mockResponseWithData(): Response<ResponseData> {
        val responseData = FakeData.fakeResponseData
        return Response.success(responseData)
    }

    @Test
    fun `test getDisaster failure`() {
        runBlocking {
            // Mocking the ApiService response to be a failed response
            val mockResponse: Response<ResponseData> = Response.error(500, "".toResponseBody())
            `when`(mockApiService.getReport("23424")).thenReturn(mockResponse)

            // Call the repository method
            val result = disasterRepository.getDisaster("23424")

            // Verify the result
            assert(!result.isSuccessful)
            assert(result.errorBody() != null)
        }
    }
}