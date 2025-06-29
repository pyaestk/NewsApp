package com.example.newsapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.local.dao.NewsDao
import com.example.newsapp.data.local.database.NewsDatabase
import com.example.newsapp.data.manager.LocalUserManagerImpl
import com.example.newsapp.data.remote.service.NewsApi
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.domain.manager.LocalUserManager
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.usecases.appEntry.AppEntryUseCases
import com.example.newsapp.domain.usecases.appEntry.ReadAppEntry
import com.example.newsapp.domain.usecases.appEntry.SaveAppEntry
import com.example.newsapp.domain.usecases.news.GetArticleByUrl
import com.example.newsapp.domain.usecases.news.GetNews
import com.example.newsapp.domain.usecases.news.NewsUseCases
import com.example.newsapp.domain.usecases.news.SearchNews
import com.example.newsapp.domain.usecases.news.bookmark.DeleteArticle
import com.example.newsapp.domain.usecases.news.bookmark.GetArticles
import com.example.newsapp.domain.usecases.news.bookmark.UpsertArticle
import com.example.newsapp.util.Contants.BASE_URL
import com.example.newsapp.util.NewsTypeConverter
import com.google.gson.Gson
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

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager {
        return LocalUserManagerImpl(application)
    }

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ): AppEntryUseCases {
        return AppEntryUseCases(
            readAppEntry = ReadAppEntry(localUserManager),
            saveAppEntry = SaveAppEntry(localUserManager)
        )

    }

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi
    ): NewsRepository = NewsRepositoryImpl(newsApi)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
        newsDao: NewsDao
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            getArticles = GetArticles(newsDao),
            upsertArticle = UpsertArticle(newsDao),
            deleteArticle = DeleteArticle(newsDao),

            getArticleByUrl = GetArticleByUrl(newsDao)
        )
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): NewsDatabase {

        return Room.databaseBuilder(
            application,
            NewsDatabase::class.java,
            "news.db")
            .addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(database: NewsDatabase) = database.newsDao()

}