package jotaemepereira.com.githubtrending

import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import jotaemepereira.com.githubtrending.managers.DataManager
import jotaemepereira.com.githubtrending.models.Owner
import jotaemepereira.com.githubtrending.models.Repo
import jotaemepereira.com.githubtrending.models.RepoSearchResponse
import jotaemepereira.com.githubtrending.networking.GithubApi
import jotaemepereira.com.githubtrending.stories.trending.GithubTrendsPresenter
import jotaemepereira.com.githubtrending.stories.trending.GithubTrendsView
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GithubTrendsPresenterTest {

    @Mock lateinit var view: GithubTrendsView
    @Mock lateinit var mockApi: GithubApi

    private lateinit var subject: GithubTrendsPresenter

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupClass() {
            RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        }
    }

    @Before
    fun setup() {
        val dataManager = DataManager(mockApi)
        subject = GithubTrendsPresenter(dataManager)
        subject.attachView(view)
    }

    @After
    fun after() {
        subject.detachView()
    }

    @Test
    fun testLoadRepos_onInit() {
        val repoResponse = createFakeListsOfRepos()
        stubDataInDataManager(Observable.just(repoResponse))

        subject.loadReposFromBeginning(false)

        verify(view).showProgress(true)
        verify(view).showProgress(false)
        verify(view).onReposLoaded(repoResponse.items)
    }

    @Test
    fun testLoadRepos_onSwipeToRefresh() {
        val repoResponse = createFakeListsOfRepos()
        stubDataInDataManager(Observable.just(repoResponse))

        subject.loadReposFromBeginning(true)

        verify(view).showProgress(true)
        verify(view).clearReposList()
        verify(view).showProgress(false)
        verify(view).onReposLoaded(repoResponse.items)
    }

    @Test
    fun testLoadRepos_onLoadingMorePages() {
        val repoResponse = createFakeListsOfRepos()
        stubDataInDataManager(Observable.just(repoResponse))

        subject.loadMoreRepos()

        verify(view).showProgress(true)
        verify(view).showProgress(false)
        verify(view).onReposLoaded(repoResponse.items)
    }

    @Test
    fun testLoadRepos_onFailure() {
        stubDataInDataManager(Observable.error(Throwable("Error!")))

        subject.loadMoreRepos()

        verify(view).showProgress(true)
        verify(view).showProgress(false)
        verify(view).onError("Error!")
    }

    private fun createFakeListsOfRepos(): RepoSearchResponse {
        val owner = Owner("Generic Owner", "test_avatar_url")
        val repo1 = Repo("Test Repo 1", "Description", 100, 100, owner)
        val repo2 = Repo("Test Repo 2", "Description", 100, 100, owner)
        val repo3 = Repo("Test Repo 3", "Description", 100, 100, owner)

        return RepoSearchResponse(2, true, mutableListOf(repo1, repo2, repo3))
    }

    private fun stubDataInDataManager(repoSearchResponse: Observable<RepoSearchResponse>) {
        `when`(mockApi.getTrendingRepos(
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        )).thenReturn(repoSearchResponse)
    }
}