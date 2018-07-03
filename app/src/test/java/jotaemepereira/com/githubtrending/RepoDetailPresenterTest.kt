package jotaemepereira.com.githubtrending

import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import jotaemepereira.com.githubtrending.managers.DataManager
import jotaemepereira.com.githubtrending.models.Owner
import jotaemepereira.com.githubtrending.models.Readme
import jotaemepereira.com.githubtrending.models.Repo
import jotaemepereira.com.githubtrending.networking.GithubApi
import jotaemepereira.com.githubtrending.stories.repoDetail.RepoDetailPresenter
import jotaemepereira.com.githubtrending.stories.repoDetail.RepoDetailView
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepoDetailPresenterTest {

    @Mock lateinit var view: RepoDetailView
    @Mock lateinit var mockApi: GithubApi

    private lateinit var subject: RepoDetailPresenter

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
        subject = RepoDetailPresenter(dataManager)
        subject.attachView(view)
    }

    @After
    fun after() {
        subject.detachView()
    }

    @Test
    fun testLoadReadme_onSuccess() {
        stubDataInDataManager(Observable.just(Readme("content", "base64")))

        subject.loadReadme(createFakeRepo())

        verify(view).showProgress(true)
        verify(view).showProgress(false)
        verify(view).onReadmeLoaded("content")
    }

    @Test
    fun testLoadReadme_onError() {
        stubDataInDataManager(Observable.error(Throwable("Error!")))

        subject.loadReadme(createFakeRepo())

        verify(view).showProgress(true)
        verify(view).showProgress(false)
        verify(view).onError("Error!")
    }

    private fun createFakeRepo(): Repo {
        val owner = Owner("Generic Owner", "test_avatar_url")
        return Repo("Test Repo 3", "Description", 100, 100, owner)
    }

    private fun stubDataInDataManager(readme: Observable<Readme>) {
        Mockito.`when`(mockApi.getReadme(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        )).thenReturn(readme)
    }
}