package com.dariomartin.talentoapp.di

import com.dariomartin.talentoapp.BuildConfig
import com.dariomartin.talentoapp.data.remote.CharactersApi
import com.dariomartin.talentoapp.data.remote.ServerDataSource
import com.dariomartin.talentoapp.data.repository.CharactersRepositoryImpl
import com.dariomartin.talentoapp.data.repository.IRemoteDataSource
import com.dariomartin.talentoapp.domain.repository.ICharactersRepository
import com.dariomartin.talentoapp.domain.usecases.CharactersUseCases
import com.dariomartin.talentoapp.domain.usecases.GetCharacterDetailsUseCase
import com.dariomartin.talentoapp.domain.usecases.GetCharactersUseCase
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
    fun provideCharactersUseCases(repository: ICharactersRepository): CharactersUseCases {
        return CharactersUseCases(
            getCharactersUseCase = GetCharactersUseCase(repository),
            getCharacterDetailsUseCase = GetCharacterDetailsUseCase(repository)
        )
    }

}