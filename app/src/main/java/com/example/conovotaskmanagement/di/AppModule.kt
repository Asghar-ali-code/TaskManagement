package com.example.conovotaskmanagement.di

import android.app.Application
import androidx.room.Room
import com.example.conovotaskmanagement.db.Database
import com.example.conovotaskmanagement.repo.Repository
import com.example.conovotaskmanagement.repo.RepositoryInf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): Database {
        return Room.databaseBuilder(
            app,
            Database::class.java,
            "task_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: Database): RepositoryInf{
        return Repository(db.dao)
    }
}