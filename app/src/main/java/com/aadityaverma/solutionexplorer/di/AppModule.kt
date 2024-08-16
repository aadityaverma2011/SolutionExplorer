package com.aadityaverma.solutionexplorer.di

import android.content.SharedPreferences
import com.aadityaverma.solutionexplorer.data.api.ApiService
import com.aadityaverma.solutionexplorer.data.repository.DetailRespositoryImpl
import com.aadityaverma.solutionexplorer.domain.repository.DetailRespository
import com.aadityaverma.solutionexplorer.domain.usecases.Exploreusecases
import com.aadityaverma.solutionexplorer.domain.usecases.GetDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val BaseUrl= "https://us-central1-walprototype.cloudfunctions.net"

    @Provides
    @Singleton
    fun provideDataApi(): ApiService{
        return Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMusicRepository(
        apiService: ApiService,

    ): DetailRespository{
        return DetailRespositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesUseCases(
        detailRespository: DetailRespository,
    ): Exploreusecases{
        return Exploreusecases(
            getDetails = GetDetails(detailRespository)
        )
    }
}