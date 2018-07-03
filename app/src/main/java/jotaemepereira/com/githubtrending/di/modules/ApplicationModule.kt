package jotaemepereira.com.githubtrending.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import jotaemepereira.com.githubtrending.di.ApplicationContext
import jotaemepereira.com.githubtrending.networking.GithubApi
import jotaemepereira.com.githubtrending.networking.GithubRESTApi
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideRestApi(): GithubApi = GithubRESTApi.create()

    @Provides
    internal fun provideApplication(): Application = application

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context = application
}