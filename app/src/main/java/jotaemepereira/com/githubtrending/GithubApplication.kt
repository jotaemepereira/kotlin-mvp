package jotaemepereira.com.githubtrending

import android.app.Application
import android.content.Context
import jotaemepereira.com.githubtrending.di.components.ApplicationComponent
import jotaemepereira.com.githubtrending.di.components.DaggerApplicationComponent
import jotaemepereira.com.githubtrending.di.modules.ApplicationModule

class GithubApplication: Application() {

    companion object {
        @JvmStatic lateinit var graph: ApplicationComponent
        @JvmStatic
        fun get(context: Context): GithubApplication = context.applicationContext as GithubApplication
    }

    override fun onCreate() {
        super.onCreate()

        graph = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    fun setComponent(component: ApplicationComponent) {
        graph = component
    }
}