package jotaemepereira.com.githubtrending.stories.trending.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jotaemepereira.com.githubtrending.R
import jotaemepereira.com.githubtrending.models.Repo
import jotaemepereira.com.githubtrending.utils.listen
import kotlinx.android.synthetic.main.layout_item_repo.view.*

class TrendingReposAdapter(val repos: ArrayList<Repo>, val itemClickListener: (Repo) -> Unit): RecyclerView.Adapter<TrendingReposAdapter.RepoViewHolder>() {

    var filteredData: ArrayList<Repo>

    init {
        filteredData = ArrayList()
        filteredData.addAll(repos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
            return RepoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_repo, parent, false))
                    .listen { position, _ ->
                        val item = filteredData[position]
                        itemClickListener(item)
                    }
    }

    override fun getItemCount(): Int = filteredData.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.onBindViewHolder(filteredData[position])
    }

    fun updateResults(newRepos: ArrayList<Repo>) {
        repos.addAll(newRepos)
        filteredData.clear()
        filteredData.addAll(repos)

        notifyDataSetChanged()
    }

    fun clear() {
        repos.clear()
        filteredData.clear()

        notifyDataSetChanged()
    }

    fun performSearch(query: String) {
        filteredData.clear()
        filteredData.addAll(repos.filter { repo ->
            repo.description.contains(query)
                    || repo.name.contains(query) })

        notifyDataSetChanged()
    }

    class RepoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun onBindViewHolder(repo: Repo) {
            itemView.repoTitle.text = repo.name
            itemView.starsCount.text = repo.stargazers_count.toString()
            itemView.description.text = repo.description
        }
    }
}