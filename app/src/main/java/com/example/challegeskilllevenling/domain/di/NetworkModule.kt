package com.example.challegeskilllevenling.domain.di


import com.example.challegeskilllevenling.data.database.service.MercadoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.mercadolibre.com/"
/**
provides a single retrofit instance
 * */
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    /**
    provides a single api interface  instance
     * */
    @Singleton
    @Provides
    fun provideMercadoApi(retrofit: Retrofit): MercadoApi {
        return retrofit.create(MercadoApi::class.java)
    }

}