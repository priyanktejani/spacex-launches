package com.spacex.launches.feature.rocketlaunches

import android.content.Context
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.spacex.launches.core.common.util.getStatusResources
import com.spacex.launches.core.model.data.RocketLaunch
import com.spacex.launches.core.common.R.color

/**
 * Custom view to show a [RocketLaunch] in the list in the list fragment.
 */
class ListItemView(context: Context) : CardView(context) {

    private val missionNameText: TextView
    private val launchStatusText: TextView

    init {
        inflate(context, R.layout.list_item_view, this)
        setBackgroundColor(ContextCompat.getColor(context, color.colorBackground))
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        params.marginStart = 40
        params.marginEnd = 40
        layoutParams = params
        missionNameText = findViewById(R.id.missionNameText)
        launchStatusText = findViewById(R.id.launchStatusText)
    }

    fun setItem(listItem: RocketLaunch) {
        val (statusTextResId, statusColorResId) = listItem.getStatusResources()

        missionNameText.text = listItem.name
        launchStatusText.apply {
            text = context.getString(statusTextResId)
            setTextColor(statusColorResId)
        }
    }

}
