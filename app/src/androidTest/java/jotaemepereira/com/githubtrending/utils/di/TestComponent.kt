package jotaemepereira.com.githubtrending.utils.di

import dagger.Component
import jotaemepereira.com.githubtrending.di.components.ApplicationComponent
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationTestModule::class))
interface TestComponent : ApplicationComponent {

}