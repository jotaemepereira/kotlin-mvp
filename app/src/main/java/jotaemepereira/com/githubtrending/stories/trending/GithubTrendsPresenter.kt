package jotaemepereira.com.githubtrending.stories.trending

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jotaemepereira.com.githubtrending.base.Presenter
import jotaemepereira.com.githubtrending.managers.DataManager
import jotaemepereira.com.githubtrending.models.RepoSearchResponse
import javax.inject.Inject

class GithubTrendsPresenter @Inject constructor(val dataManager: DataManager): Presenter<GithubTrendsView> {

    private val firstPage = 1
    private lateinit var view: GithubTrendsView
    private var disposable: Disposable? = null
    private var page = firstPage
    private var isManualRefresh = false

    override fun attachView(mvpView: GithubTrendsView) {
        view = mvpView
    }

    override fun detachView() {
        disposable?.dispose()
    }

    fun loadReposFromBeginning(isManualRefresh: Boolean) {
        page = firstPage
        this.isManualRefresh = isManualRefresh
        loadRepos()
    }

    fun loadMoreRepos() {
        page++
        isManualRefresh = false
        loadRepos()
    }

    private fun loadRepos() {
        view.showProgress(true)
        disposable = dataManager.getTrendingRepos(page, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> onSuccessGetRepos(result) },
                        { error -> onError(error) }
                )
    }

    private fun onSuccessGetRepos(result: RepoSearchResponse) {
        if (isManualRefresh) {
            view.clearReposList()
        }

        view.showProgress(false)
        view.onReposLoaded(result.items)
    }

    override fun onError(throwable: Throwable) {
        view.showProgress(false)
        view.onError(throwable.localizedMessage)
    }
}