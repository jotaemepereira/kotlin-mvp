package jotaemepereira.com.githubtrending.utils.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import jotaemepereira.com.githubtrending.di.ApplicationContext
import jotaemepereira.com.githubtrending.managers.DataManager
import jotaemepereira.com.githubtrending.networking.GithubApi
import org.mockito.Mockito
import javax.inject.Singleton

@Module
class ApplicationTestModule(protected val application: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun providesDataManager(): DataManager {
        return DataManager(Mockito.mock(GithubApi::class.java))
    }
}