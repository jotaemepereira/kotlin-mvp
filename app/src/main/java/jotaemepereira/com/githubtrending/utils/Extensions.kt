package jotaemepereira.com.githubtrending.utils

import android.support.v7.widget.RecyclerView

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }

    return this
}