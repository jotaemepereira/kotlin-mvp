package jotaemepereira.com.githubtrending.base

interface Presenter<in T: BaseView> {

    fun attachView(mvpView: T)
    fun detachView()
    fun onError(throwable: Throwable)
}