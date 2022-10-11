package com.jisoo.runningapp.di

import android.content.Context
import androidx.room.Room
import com.jisoo.runningapp.db.RunDAO
import com.jisoo.runningapp.db.RunningDatabase
import com.jisoo.runningapp.other.Constant.RUNNING_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * SingletonComponent를 사용하는 것만으로는 싱글톤으로 만들어 지지 않는다.
 * 함수명 위에 singleton 어노테이션을 붙여주어야 싱글톤으로 생성
 * */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        RunningDatabase::class.java,
        RUNNING_DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun provideRunDao(database: RunningDatabase) = database.getRunDao()

}