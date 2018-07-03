package jotaemepereira.com.githubtrending.utils

import android.content.Context
import jotaemepereira.com.githubtrending.GithubApplication
import jotaemepereira.com.githubtrending.managers.DataManager
import jotaemepereira.com.githubtrending.utils.di.ApplicationTestModule
import jotaemepereira.com.githubtrending.utils.di.DaggerTestComponent
import jotaemepereira.com.githubtrending.utils.di.TestComponent
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TestComponentRule(val context: Context) : TestRule {
    private lateinit var testComponent: TestComponent

    fun getMockDataManager(): DataManager {
        return testComponent.dataManager()
    }

    private fun setupDaggerTestComponentInApplication() {
        val application = GithubApplication.get(context)
        testComponent = DaggerTestComponent
                .builder()
                .applicationTestModule(ApplicationTestModule(application))
                .build()

        application.setComponent(testComponent)
    }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                try {
                    setupDaggerTestComponentInApplication()
                    base.evaluate()
                } finally {

                }
            }
        }

    }
}