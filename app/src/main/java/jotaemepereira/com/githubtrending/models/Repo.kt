package jotaemepereira.com.githubtrending.models

import java.io.Serializable

data class RepoSearchResponse(
        val totalCount: Int,
        val incompleteResults: Boolean,
        val items: List<Repo>
)

data class Repo(
        val name: String,
        val description:
        String,
        val stargazers_count: Int,
        val forks: Int,
        val owner: Owner
): Serializable

data class Owner(val login: String, val avatar_url: String): Serializable