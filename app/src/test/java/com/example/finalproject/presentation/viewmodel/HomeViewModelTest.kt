package com.example.finalproject.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.finalproject.data.ApiService
import com.example.finalproject.data.repository.DisasterRepositoryImpl
import com.example.finalproject.domain.repository.DisasterRepository
import com.example.finalproject.domain.usecase.FilterDisasterUseCase
import com.example.finalproject.domain.usecase.GetDisasterUseCase
import com.example.finalproject.domain.usecase.NotificationUseCase
import com.example.finalproject.domain.usecase.SearchDisasterUseCase
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var mockApiService: ApiService

    @Mock
    lateinit var useCase: GetDisasterUseCase

    @Mock
    lateinit var notificationUseCase: NotificationUseCase

    @Mock
    lateinit var filterDisasterUseCase: FilterDisasterUseCase

    @Mock
    lateinit var searchDisasterUseCase: SearchDisasterUseCase

    private lateinit var repository: DisasterRepository
    private lateinit var homeViewModel: HomeViewModel


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        repository = DisasterRepositoryImpl(mockApiService)
        homeViewModel = HomeViewModel(
            useCaseGet = GetDisasterUseCase(repository), mock(), mock(), mock()
        )

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test get data function `() = runBlocking {

    }
}
