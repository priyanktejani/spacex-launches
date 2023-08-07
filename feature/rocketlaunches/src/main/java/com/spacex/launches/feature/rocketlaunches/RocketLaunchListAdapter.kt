package com.spacex.launches.feature.rocketlaunches

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spacex.launches.core.model.data.RocketLaunch

/**
 * ListAdapter class for the Recycler view that displays a list of [RocketLaunch] objects.
 */
class RocketLaunchListAdapter internal constructor(
    private val context: Context,
    private val itemClickListener: OnItemClickListener
    ) :
    ListAdapter<RocketLaunch, RocketLaunchListAdapter.ListItemViewHolder>(
        RocketLaunchListDiffCallback()
    ) {

    inner class ListItemViewHolder(val listItemView: ListItemView) :
        RecyclerView.ViewHolder(listItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(ListItemView(context))
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.listItemView.setItem(item)
        holder.listItemView.setOnClickListener {
            itemClickListener.onItemClick(item)
        }
    }
}

interface OnItemClickListener {
    fun onItemClick(rocketLaunch: RocketLaunch)
}
