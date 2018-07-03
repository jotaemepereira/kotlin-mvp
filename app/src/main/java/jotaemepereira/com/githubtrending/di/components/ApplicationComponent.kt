package jotaemepereira.com.githubtrending.di.components

import android.app.Application
import android.content.Context
import dagger.Component
import jotaemepereira.com.githubtrending.di.ApplicationContext
import jotaemepereira.com.githubtrending.di.modules.ApplicationModule
import jotaemepereira.com.githubtrending.managers.DataManager
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    @ApplicationContext fun context(): Context
    fun application(): Application
    fun dataManager(): DataManager
}