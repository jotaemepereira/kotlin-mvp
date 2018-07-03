package jotaemepereira.com.githubtrending.stories.trending

import jotaemepereira.com.githubtrending.base.BaseView
import jotaemepereira.com.githubtrending.models.Repo

interface GithubTrendsView: BaseView {

    fun onReposLoaded(repos: List<Repo>)
    fun showProgress(showProgress: Boolean)
    fun clearReposList()
}