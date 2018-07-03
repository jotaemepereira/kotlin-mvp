package jotaemepereira.com.githubtrending.managers

import io.reactivex.Observable
import jotaemepereira.com.githubtrending.models.Readme
import jotaemepereira.com.githubtrending.models.RepoSearchResponse
import jotaemepereira.com.githubtrending.networking.GithubApi
import javax.inject.Inject

open class DataManager @Inject constructor(val api: GithubApi) {

    fun getTrendingRepos(page: Int, search: String?): Observable<RepoSearchResponse> {
        search?.let {
            return api.getTrendingRepos(page, "stars", "desc", it)
        }

        return api.getTrendingRepos(page, "stars", "desc", "android")
    }

    fun getRepoReadme(owner: String, repoName: String): Observable<Readme> {
        return api.getReadme(owner, repoName)
    }
}