package jotaemepereira.com.githubtrending

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeDown
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.rule.IntentsTestRule
import io.reactivex.Observable
import jotaemepereira.com.githubtrending.models.Owner
import jotaemepereira.com.githubtrending.models.Repo
import jotaemepereira.com.githubtrending.models.RepoSearchResponse
import jotaemepereira.com.githubtrending.stories.trending.GithubTrendsActivity
import jotaemepereira.com.githubtrending.stories.trending.adapters.TrendingReposAdapter
import jotaemepereira.com.githubtrending.utils.RecyclerViewItemCountAssertion
import jotaemepereira.com.githubtrending.utils.TestComponentRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import jotaemepereira.com.githubtrending.models.Readme
import jotaemepereira.com.githubtrending.stories.repoDetail.RepoDetailActivity
import org.hamcrest.Matchers.allOf


@RunWith(AndroidJUnit4::class)
class GithubTrendsActivityTest {

    val component = TestComponentRule(InstrumentationRegistry.getTargetContext())
    val main: IntentsTestRule<GithubTrendsActivity> = IntentsTestRule(
            GithubTrendsActivity::class.java,
            false,
            false)

    @get:Rule
    public var chain: TestRule = RuleChain.outerRule(component).around(main)

    @Test
    fun testListHasCorrectSize_onSuccessNetworkLoading() {
        stubDataInDataManager(Observable.just(createFakeListsOfRepos()))

        main.launchActivity(Intent(component.context, GithubTrendsActivity::class.java))

        onView(withId(R.id.recycler)).check(RecyclerViewItemCountAssertion(3))
    }

    @Test
    fun testListHasCorrectSize_onScrollForMore() {
        stubDataInDataManager(Observable.just(createFakeListsOfRepos()))

        main.launchActivity(Intent(component.context, GithubTrendsActivity::class.java))

        onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition<TrendingReposAdapter.RepoViewHolder>(10))
        onView(withId(R.id.recycler)).check(RecyclerViewItemCountAssertion(6))
    }

    @Test
    fun testListHasCorrectSize_onSwipeToRefresh() {
        stubDataInDataManager(Observable.just(createFakeListsOfRepos()))

        main.launchActivity(Intent(component.context, GithubTrendsActivity::class.java))

        onView(withId(R.id.recycler)).perform(swipeDown())
        onView(withId(R.id.recycler)).check(RecyclerViewItemCountAssertion(3))
    }

    @Test
    fun testNetworkError_onLoadingRepos() {
        stubDataInDataManager(Observable.error(Throwable("Error!")))

        main.launchActivity(Intent(component.context, GithubTrendsActivity::class.java))

        checkSnackbarWithText("Error!")
    }

    @Test
    fun testRepoDetailIsCorrectlyLoaded_onItemClick() {
        val reposResponse = createFakeListsOfRepos()
        stubReadmeDataInDataManager(Observable.just(Readme("content", "base64")))
        stubDataInDataManager(Observable.just(reposResponse))

        main.launchActivity(Intent(component.context, GithubTrendsActivity::class.java))

        onView(withId(R.id.recycler)).perform(RecyclerViewActions.actionOnItemAtPosition<TrendingReposAdapter.RepoViewHolder>(0, click()))

        intended(allOf(
                hasComponent(RepoDetailActivity::class.java.getName()),
                hasExtra("repo", reposResponse.items[0]))
        )
    }

    private fun createFakeListsOfRepos(): RepoSearchResponse {
        val owner = Owner("Generic Owner", "test_avatar_url")
        val repo1 = Repo("Test Repo 1", "Description", 100, 100, owner)
        val repo2 = Repo("Test Repo 2", "Description", 100, 100, owner)
        val repo3 = Repo("Test Repo 3", "Description", 100, 100, owner)

        return RepoSearchResponse(2, true, mutableListOf(repo1, repo2, repo3))
    }

    private fun checkSnackbarWithText(message: String) {
        onView(withText(message))
                .check(matches(withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE
                )));
    }


    private fun stubDataInDataManager(repoSearchResponse: Observable<RepoSearchResponse>) {
        Mockito.`when`(
                component.getMockDataManager().api.getTrendingRepos(
                        ArgumentMatchers.anyInt(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                )
        ).thenReturn(repoSearchResponse)
    }

    private fun stubReadmeDataInDataManager(readme: Observable<Readme>) {
        Mockito.`when`(
                component.getMockDataManager().api.getReadme(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                )
        ).thenReturn(readme)
    }

}
