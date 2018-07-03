package jotaemepereira.com.githubtrending.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jotaemepereira.com.githubtrending.GithubApplication
import jotaemepereira.com.githubtrending.di.components.ActivityComponent
import jotaemepereira.com.githubtrending.di.components.DaggerActivityComponent

open class BaseActivity: AppCompatActivity() {

    lateinit var graph: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        graph = DaggerActivityComponent
                .builder()
                .applicationComponent(GithubApplication.graph)
                .build()
    }

}