package hu.bme.aut.android.devicemanager.ui.requestmanager.list

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentRequestsListBinding
import hu.bme.aut.android.devicemanager.domain.model.RentalRequest
import hu.bme.aut.android.devicemanager.util.showSnackBar

@AndroidEntryPoint
class RequestsListFragment : RainbowCakeFragment<RequestsListViewState, RequestsListViewModel>(),
    RequestListAdapter.RequestItemClickListener {

    private lateinit var binding: FragmentRequestsListBinding
    override fun provideViewModel() = getViewModelFromFactory()
    private lateinit var requestListAdapter: RequestListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.loadRentalRequests()
    }

    override fun render(viewState: RequestsListViewState) {
        when (viewState) {
            Initial -> {
                binding.loading.isVisible = false
                binding.noDevicesText.isVisible = false
            }
            RentalRequestsLoading -> {
                binding.loading.isVisible = true
                binding.noDevicesText.isVisible = false
            }
            is RentalRequestsError -> {
                binding.loading.isVisible = false
                binding.noDevicesText.isVisible = false
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(binding.root, errorColor, viewState.errorMessage)
            }
            is RentalRequestsReady -> {
                binding.loading.isVisible = false
                binding.noDevicesText.isVisible = false
                requestListAdapter.addAllRentalRequests(viewState.requests)
                if (viewState.requests.isEmpty()) {
                    binding.noDevicesText.isVisible = true
                }
            }
        }
    }

    private fun setupRecyclerView() {
        requestListAdapter = RequestListAdapter()
        binding.rvRentalRequest.layoutManager = LinearLayoutManager(context)
        requestListAdapter.itemClickListener = this
        binding.rvRentalRequest.adapter = requestListAdapter
    }

    override fun onItemClick(rentalRequest: RentalRequest) {
        findNavController().navigate(
            RequestsListFragmentDirections.actionRequestsListFragmentToRentalRequestDetailsFragment(
                rentalRequest.id
            )
        )
    }
}