package jotaemepereira.com.githubtrending.stories.repoDetail

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jotaemepereira.com.githubtrending.base.Presenter
import jotaemepereira.com.githubtrending.managers.DataManager
import jotaemepereira.com.githubtrending.models.Readme
import jotaemepereira.com.githubtrending.models.Repo
import javax.inject.Inject

class RepoDetailPresenter @Inject constructor(val dataManager: DataManager): Presenter<RepoDetailView> {

    lateinit var view: RepoDetailView
    private var disposable: Disposable? = null

    override fun attachView(mvpView: RepoDetailView) {
        view = mvpView
    }

    override fun detachView() {
        disposable?.dispose()
    }

    fun loadReadme(repo: Repo) {
        view.showProgress(true)
        disposable = dataManager.getRepoReadme(repo.owner.login, repo.name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> onSuccessGetReadme(result) },
                        { error -> onError(error) }
                )
    }

    private fun onSuccessGetReadme(readme: Readme) {
        view.showProgress(false)
        view.onReadmeLoaded(readme.content)
    }

    override fun onError(throwable: Throwable) {
        view.showProgress(false)
        view.onError(throwable.localizedMessage)
    }

}