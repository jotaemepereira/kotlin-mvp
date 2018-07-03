package jotaemepereira.com.githubtrending

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import io.reactivex.Observable
import jotaemepereira.com.githubtrending.models.Owner
import jotaemepereira.com.githubtrending.models.Readme
import jotaemepereira.com.githubtrending.models.Repo
import jotaemepereira.com.githubtrending.stories.repoDetail.RepoDetailActivity
import jotaemepereira.com.githubtrending.utils.TestComponentRule
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class RepoDetailActivityTest {

    val component = TestComponentRule(InstrumentationRegistry.getTargetContext())
    val main: IntentsTestRule<RepoDetailActivity> = IntentsTestRule(
            RepoDetailActivity::class.java,
            false,
            false)

    @get:Rule
    public var chain: TestRule = RuleChain.outerRule(component).around(main)

    @Test
    fun testUI_isCorrectlyLoadedWithRepoExtra() {
        stubReadmeDataInDataManager(Observable.just(Readme("content", "base64")))

        val intent = Intent()
        intent.putExtra("repo", createFakeRepo())
        main.launchActivity(intent)

        onView(withId(R.id.userName)).check(matches(withText("Owner Test")))
        onView(withId(R.id.description)).check(matches(withText("This is a description")))
        onView(withId(R.id.stars)).check(matches(withText("100 Stars")))
        onView(withId(R.id.forks)).check(matches(withText("101 Forks")))
    }

    @Test
    fun testNetworkError_onLoadingReadme() {
        stubReadmeDataInDataManager(Observable.error(Throwable("Error!")))

        val intent = Intent()
        intent.putExtra("repo", createFakeRepo())
        main.launchActivity(intent)

        checkSnackbarWithText("Error!")
    }

    private fun checkSnackbarWithText(message: String) {
        onView(withText(message))
                .check(matches(ViewMatchers.withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE
                )));
    }

    private fun createFakeRepo(): Repo {
        val owner = Owner("Owner Test", "test_avatar_url")
        return Repo("Test Repo", "This is a description" , 100, 101, owner)
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