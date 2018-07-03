package jotaemepereira.com.githubtrending.di.components

import dagger.Component
import jotaemepereira.com.githubtrending.di.PerActivity
import jotaemepereira.com.githubtrending.di.modules.ActivityModule
import jotaemepereira.com.githubtrending.stories.repoDetail.RepoDetailActivity
import jotaemepereira.com.githubtrending.stories.trending.GithubTrendsActivity

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(githubTrendsActivity: GithubTrendsActivity)
    fun inject(repoDetailActivity: RepoDetailActivity)
}