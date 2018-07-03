package jotaemepereira.com.githubtrending.di.modules

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import jotaemepereira.com.githubtrending.di.ActivityContext

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    internal fun provideActivity(): Activity = activity

    @Provides
    @ActivityContext
    internal fun providesContext(): Context = activity

}