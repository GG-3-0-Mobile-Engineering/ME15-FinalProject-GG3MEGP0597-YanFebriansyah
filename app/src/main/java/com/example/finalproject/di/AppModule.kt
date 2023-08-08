package com.example.finalproject.di

import com.example.finalproject.data.ApiService
import com.example.finalproject.data.repository.DisasterRepositoryImpl
import com.example.finalproject.domain.repository.DisasterRepository
import com.example.finalproject.domain.usecase.GetDisasterUseCase
import com.example.finalproject.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDisasterApi(): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideRepositoryDisaster(apiService: ApiService): DisasterRepository {
        return DisasterRepositoryImpl(apiService)
    }


    @Provides
    @Singleton
    fun provideUsecase(repository: DisasterRepository): GetDisasterUseCase {
        return GetDisasterUseCase(repository)
    }


}