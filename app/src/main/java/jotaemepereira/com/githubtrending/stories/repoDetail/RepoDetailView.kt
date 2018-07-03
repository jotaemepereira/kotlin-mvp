package jotaemepereira.com.githubtrending.stories.repoDetail

import jotaemepereira.com.githubtrending.base.BaseView

interface RepoDetailView: BaseView {

    fun onReadmeLoaded(readmeString: String)
    fun showProgress(show: Boolean)
}