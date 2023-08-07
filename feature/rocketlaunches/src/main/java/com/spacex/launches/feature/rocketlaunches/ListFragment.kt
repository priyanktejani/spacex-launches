package com.spacex.launches.feature.rocketlaunches

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.spacex.launches.core.model.data.RocketLaunch
import com.spacex.launches.feature.rocketlaunches.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.spacex.launches.core.common.R

/**
 * Fragment to display the list of Rocket Launches.
 */
@AndroidEntryPoint
class ListFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = _binding!!

    private val viewModel: RocketLaunchesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment.
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Retrieve data for the Rocket Launches.
        viewModel.retrieveData()
        setupList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Setup the list adapter and give it it's data to be displayed.
    private fun setupList() {
        RocketLaunchListAdapter(requireContext(), this).apply {
            binding.rocketLaunchList.adapter = this
            binding.rocketLaunchList.addItemDecoration(ListItemDecoration(20))
            lifecycleScope.launch {
                viewModel.state.collect { result ->
                    // Handle the loading and Empty state and update the UI accordingly.
                    handleViewState(result)

                    // Update the adaptor data based on the RocketLaunchesUiState.
                    updateAdapterData(result, this@apply)
                }
            }
        }
    }

    // Handle the loading and Empty state and update the UI accordingly.
    private fun handleViewState(result: RocketLaunchesUiState) {
        // Update the visibility of the no data text based on the RocketLaunchesUiState.
        binding.noDataText.visibility = when (result) {
            RocketLaunchesUiState.Empty -> View.VISIBLE
            RocketLaunchesUiState.Loading -> View.GONE
            is RocketLaunchesUiState.Error -> View.GONE
            is RocketLaunchesUiState.Success -> View.GONE
        }

        // Update the visibility of the loading indicator based on the RocketLaunchesUiState.
        binding.loadingIndicator.visibility = when (result) {
            RocketLaunchesUiState.Empty -> View.GONE
            RocketLaunchesUiState.Loading -> View.VISIBLE
            is RocketLaunchesUiState.Error -> View.GONE
            is RocketLaunchesUiState.Success -> View.GONE
        }
    }

    private fun updateAdapterData(result: RocketLaunchesUiState, adapter: RocketLaunchListAdapter) {
        if (result is RocketLaunchesUiState.Success) {
            // Update the RecyclerView adapter with the list of RocketLaunches if the result is Success.
            adapter.submitList(result.rocketLaunches)
        } else if (result is RocketLaunchesUiState.Error) {
            if (!result.rocketLaunches.isNullOrEmpty()) {
                // If there are RocketLaunches in the error state but list is not empty, still update the adapter with them.
                adapter.submitList(result.rocketLaunches)
            }

            // Show an error toast with the error message if available.
            Toast.makeText(context, result.errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemClick(rocketLaunch: RocketLaunch) {
        // Handle click on a RocketLaunch item and navigate to its details screen through DeepLink.
        val flightNumber = rocketLaunch.flightNumber
        val rocketLaunchListUri = Uri.parse(
            getString(R.string.rocket_launch_details_deep_link)
                .replace("{flightNumber}", flightNumber.toString())
        )
        val rocketLaunchListUriDeepLink = NavDeepLinkRequest.Builder
            .fromUri(rocketLaunchListUri)
            .build()
        findNavController().navigate(rocketLaunchListUriDeepLink)
    }

}
