package jotaemepereira.com.githubtrending.stories.repoDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.util.Base64
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import jotaemepereira.com.githubtrending.R
import jotaemepereira.com.githubtrending.base.BaseActivity
import jotaemepereira.com.githubtrending.models.Repo
import jotaemepereira.com.githubtrending.utils.SnackbarUtils
import kotlinx.android.synthetic.main.activity_github_trends.*
import kotlinx.android.synthetic.main.activity_repo_detail.*
import javax.inject.Inject

class RepoDetailActivity: BaseActivity(), RepoDetailView {

    @Inject
    lateinit var presenter: RepoDetailPresenter
    lateinit var repo: Repo

    companion object {
        private const val REPO = "repo"

        fun newIntent(context: Context, repo: Repo): Intent =
                Intent(context, RepoDetailActivity::class.java).apply {
                    putExtra(REPO, repo)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)

        graph.inject(this);
        presenter.attachView(this)
        repo = intent.extras["repo"] as Repo

        setupActionBar()
        loadUI()

        presenter.loadReadme(repo)
    }

    private fun setupActionBar() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = repo.name
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadUI() {
        Glide.with(this)
                .load(repo.owner.avatar_url)
                .into(userProfileImage)

        userName.text = repo.owner.login
        description.text = repo.description
        stars.text = "${repo.stargazers_count.toString()} Stars"
        forks.text = "${repo.forks.toString()} Forks"
    }

    override fun showProgress(show: Boolean) {
        when(show) {
            true -> progressBar.visibility = View.VISIBLE
            false -> {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onReadmeLoaded(readmeString: String) {
        val decodedMarkdown = Base64.decode(readmeString, Base64.DEFAULT).toString(charset("UTF-8"))
        readme.loadMarkdown(decodedMarkdown)
    }

    override fun onError(errorMessage: String) {
        SnackbarUtils.showErrorMessage(errorMessage, findViewById(android.R.id.content))
    }


}