package com.spacex.launches.feature.rocketlaunch_details.util

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(inputDateTime: String): String {
    // Input date-time format
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

    // Output date-time format
    val outputFormat = SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault())

    // Parse the input date-time
    val date = inputFormat.parse(inputDateTime)

    // Format the parsed date-time into the desired output format
    return outputFormat.format(date!!)
}