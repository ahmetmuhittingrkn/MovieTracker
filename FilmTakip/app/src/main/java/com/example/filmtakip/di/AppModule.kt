package com.example.filmtakip.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.filmtakip.data.entity.FavoriteMovie
import com.example.filmtakip.data.repository.MovieRepository
import com.example.filmtakip.retrofit.ApiService
import com.example.filmtakip.retrofit.ApiUtils
import com.example.filmtakip.room.AppDatabase
import com.example.filmtakip.room.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiUtils.getApiService()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(apiService: ApiService,favoriteMovieDao: FavoriteMovieDao) : MovieRepository {
        return MovieRepository(apiService,favoriteMovieDao)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(context,AppDatabase::class.java,"app_database").build()
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieDao(appDatabase: AppDatabase): FavoriteMovieDao {
        return appDatabase.favoriteMovieDao()
    }

}