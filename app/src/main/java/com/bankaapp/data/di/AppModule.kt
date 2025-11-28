package com.bankaapp.data.di

import android.content.Context
import androidx.room.Room
import com.bankaapp.data.dao.NoteDao
import com.bankaapp.data.db.NoteDatabase
import com.bankaapp.data.domain.repository.LoginRepository
import com.bankaapp.data.domain.repository.NoteRepository
import com.bankaapp.data.domain.repository.SignupRepository
import com.bankaapp.data.repository.LoginRepositoryImp
import com.bankaapp.data.repository.NoteRepositoryImpl
import com.bankaapp.data.repository.SignupRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppPreference(@ApplicationContext context: Context): AppPreference {
        return AppPreference(context)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(appPreference: AppPreference): LoginRepository {
        return LoginRepositoryImp(appPreference)
    }


    @Provides
    fun provideSignupRepository(appPreference: AppPreference): SignupRepository {
        return SignupRepositoryImp(appPreference)

    }

}
