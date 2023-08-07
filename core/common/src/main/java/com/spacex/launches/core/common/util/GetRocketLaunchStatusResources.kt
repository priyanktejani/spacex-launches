package com.spacex.launches.core.common.util

import com.spacex.launches.core.common.R
import com.spacex.launches.core.model.data.RocketLaunch

// Extension function for the RocketLaunch class
fun RocketLaunch.getStatusResources(): Pair<Int, Int> {
    return when {
        success == true -> Pair(R.string.successful, android.graphics.Color.GREEN)
        upcoming == false -> Pair(R.string.unsuccessful, android.graphics.Color.RED)
        else -> Pair(R.string.upcoming, android.graphics.Color.BLUE)
    }
}