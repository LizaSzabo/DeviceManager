package hu.bme.aut.android.devicemanager.ui.requestmanager.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.mockRentalRequestData
import hu.bme.aut.android.devicemanager.databinding.FragmentRequestsListBinding

@AndroidEntryPoint
class RequestsListFragment : RainbowCakeFragment<RequestsListViewState, RequestsListViewModel>() {

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
    }

    override fun render(viewState: RequestsListViewState) {
        // TODO("Not yet implemented")
    }

    private fun setupRecyclerView() {
        requestListAdapter = RequestListAdapter()
        binding.rvRentalRequest.layoutManager = LinearLayoutManager(context)
        binding.rvRentalRequest.adapter = requestListAdapter
        requestListAdapter.addAllRentalRequests(
            mockRentalRequestData
        )
    }
}