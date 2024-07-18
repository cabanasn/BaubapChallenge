package com.ircl.baubapchallenge.di

import com.ircl.baubapchallenge.data.remote.FakeLoginHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFakeLoginHandler(): FakeLoginHandler {
        return FakeLoginHandler()
    }

}