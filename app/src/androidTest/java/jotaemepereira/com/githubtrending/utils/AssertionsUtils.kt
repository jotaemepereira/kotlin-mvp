package jotaemepereira.com.githubtrending.utils

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.view.View


class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {
    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (view == null) {
            throw Resources.NotFoundException()
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assert(expectedCount == adapter.itemCount)
    }

}