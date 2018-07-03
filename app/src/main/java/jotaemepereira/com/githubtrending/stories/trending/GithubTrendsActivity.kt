package jotaemepereira.com.githubtrending.stories.trending

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import jotaemepereira.com.githubtrending.R
import jotaemepereira.com.githubtrending.base.BaseActivity
import jotaemepereira.com.githubtrending.models.Repo
import jotaemepereira.com.githubtrending.stories.repoDetail.RepoDetailActivity
import jotaemepereira.com.githubtrending.stories.trending.adapters.TrendingReposAdapter
import jotaemepereira.com.githubtrending.utils.InfiniteScrollListener
import jotaemepereira.com.githubtrending.utils.SnackbarUtils
import kotlinx.android.synthetic.main.activity_github_trends.*
import javax.inject.Inject

class GithubTrendsActivity : BaseActivity(), GithubTrendsView, SearchView.OnQueryTextListener,
        SwipeRefreshLayout.OnRefreshListener {

    @Inject lateinit var presenter: GithubTrendsPresenter
    private lateinit var scrollListener: InfiniteScrollListener

    private val repos = ArrayList<Repo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_trends)

        graph.inject(this);
        presenter.attachView(this)

        swiperefresh.setOnRefreshListener(this)
        setupRecyclerView()

        presenter.loadReposFromBeginning(false)
    }

    private fun setupRecyclerView() {
        val linearLayout = LinearLayoutManager(this)
        recycler.layoutManager = linearLayout
        recycler.adapter =  TrendingReposAdapter(repos, { repo -> onClickedRepo(repo)  })
        scrollListener = InfiniteScrollListener({ presenter.loadMoreRepos() }, linearLayout)
        recycler.addOnScrollListener(scrollListener)
    }

    private fun onClickedRepo(repo: Repo) {
        startActivity(RepoDetailActivity.newIntent(this, repo))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_trends_activity, menu)

        val searchItem = menu!!.findItem(R.id.actionSearch)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this);
        searchView.isIconified = true

        return super.onCreateOptionsMenu(menu)
    }

    override fun onReposLoaded(repos: List<Repo>) {
        val adapter = recycler.adapter as? TrendingReposAdapter
        adapter?.updateResults(ArrayList(repos))
    }

    override fun onError(errorMessage: String) {
        SnackbarUtils.showErrorMessage(errorMessage, findViewById(android.R.id.content))
    }

    override fun showProgress(showProgress: Boolean) {
        when(showProgress) {
            true -> progress.visibility = View.VISIBLE
            false -> {
                progress.visibility = View.GONE
                swiperefresh.isRefreshing = false
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { searchString ->
            filterResults(searchString)
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { searchString ->
            filterResults(searchString)
        }

        return true
    }

    override fun clearReposList() {
        val adapter = recycler.adapter as? TrendingReposAdapter
        adapter?.clear()
    }

    override fun onRefresh() {
        scrollListener.resetValues()
        presenter.loadReposFromBeginning(true)
    }

    private fun filterResults(query: String) {
        val adapter = recycler.adapter as? TrendingReposAdapter
        adapter?.performSearch(query)
    }


}
