package com.spacex.launches.feature.rocketlaunch_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.spacex.launches.core.common.util.getStatusResources
import com.spacex.launches.feature.rocketlaunch_details.databinding.FragmentDetailBinding
import com.spacex.launches.feature.rocketlaunch_details.util.formatDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A Fragment to display details of specific launches.
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = _binding!!

    private val viewModel: RocketLaunchViewModel by viewModels()
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment.
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data for the specific Rocket Launch using the provided flight number argument.
        viewModel.retrieveData(args.flightNumber.toInt())

        // Observe the data changes in the view model using a coroutine-based lifecycleScope.
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.state.collect { result ->
                // Handle the loading and Empty state and update the UI accordingly.
                handleViewState(result)

                // Set the view data based on the RocketLaunchUiState.
                setViewData(result)
            }
        }
    }


    private fun handleViewState(result: RocketLaunchUiState) {
        // Update the visibility of the no data text based on the RocketLaunchUiState.
        binding.noDataText.visibility = when (result) {
            RocketLaunchUiState.Empty -> View.VISIBLE
            RocketLaunchUiState.Loading -> View.GONE
            is RocketLaunchUiState.Error -> View.GONE
            is RocketLaunchUiState.Success -> View.GONE
        }

        // Update the visibility of the loading indicator based on the RocketLaunchUiState.
        binding.loadingIndicator.visibility = when (result) {
            RocketLaunchUiState.Empty -> View.GONE
            RocketLaunchUiState.Loading -> View.VISIBLE
            is RocketLaunchUiState.Error -> View.GONE
            is RocketLaunchUiState.Success -> View.GONE
        }
    }

    private fun setViewData(result: RocketLaunchUiState) {
        // Set the view data if the RocketLaunchUiState is Success.
        if (result is RocketLaunchUiState.Success) {
            result.rocketLaunch?.let {
                // Populate the UI with the RocketLaunch data.
                binding.flightNumberTextView.text = it.flightNumber.toString()
                binding.nameTextView.text = it.name
                binding.detailsTextView.visibility =
                    if (it.details.isNullOrEmpty()) View.GONE else View.VISIBLE
                binding.detailsTextView.text = it.details
                binding.dateTextView.text = formatDate(it.dateUTC)

                // Get status text resource ID and set the status text accordingly.
                val (statusTextResId, _) = it.getStatusResources()
                binding.statusTextView.text = getString(statusTextResId)
            }
        } else if (result is RocketLaunchUiState.Error) {
            // Show an error toast if there's an error state with an error message.
            Toast.makeText(context, result.errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Clear the view binding reference when the view is destroyed to prevent leaks.
        _binding = null
    }
}
