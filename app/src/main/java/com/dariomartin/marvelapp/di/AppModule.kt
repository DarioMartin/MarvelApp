package com.dariomartin.marvelapp.di

import com.dariomartin.marvelapp.BuildConfig
import com.dariomartin.marvelapp.data.remote.CharactersApi
import com.dariomartin.marvelapp.data.remote.ServerDataSource
import com.dariomartin.marvelapp.data.repository.CharactersRepositoryImpl
import com.dariomartin.marvelapp.data.repository.IRemoteDataSource
import com.dariomartin.marvelapp.domain.repository.ICharactersRepository
import com.dariomartin.marvelapp.domain.usecases.GetCharacterDetailsUseCase
import com.dariomartin.marvelapp.domain.usecases.GetCharactersUseCase
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
    fun providesRemoteDataSource(): IRemoteDataSource {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return ServerDataSource(retrofit.create(CharactersApi::class.java))
    }

    @Provides
    @Singleton
    fun providesCharactersRepository(remoteDataSource: IRemoteDataSource): ICharactersRepository {
        return CharactersRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideCharacterDetailsUseCase(repository: ICharactersRepository): GetCharacterDetailsUseCase {
        return GetCharacterDetailsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCharactersUseCase(repository: ICharactersRepository): GetCharactersUseCase {
        return GetCharactersUseCase(repository)
    }

}