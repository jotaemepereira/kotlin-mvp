package jotaemepereira.com.githubtrending.networking

import io.reactivex.Observable
import jotaemepereira.com.githubtrending.models.Readme
import jotaemepereira.com.githubtrending.models.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("/search/repositories")
    fun getTrendingRepos(
            @Query("page") page: Int,
            @Query("sort") sort: String,
            @Query("order") order: String,
            @Query("q") query: String
    ): Observable<RepoSearchResponse>

    @GET("/repos/{owner}/{repoName}/readme")
    fun getReadme(
            @Path("owner") owner: String,
            @Path("repoName") repoName: String
    ): Observable<Readme>
}