package hu.bme.aut.android.devicemanager.ui.devicemanager.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentDevicesListBinding
import hu.bme.aut.android.devicemanager.domain.model.Device
import hu.bme.aut.android.devicemanager.domain.model.DeviceRentalState

@AndroidEntryPoint
class DevicesListFragment : RainbowCakeFragment<DevicesListViewState, DevicesListViewModel>(),
    DevicesListAdapter.DeviceItemClickListener {

    private lateinit var binding: FragmentDevicesListBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_devices_list
    private lateinit var devicesListAdapter: DevicesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDevicesListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    override fun render(viewState: DevicesListViewState) {
        //TODO("Not yet implemented")
    }

    private fun setupRecyclerView() {
        devicesListAdapter = DevicesListAdapter()
        binding.rvDevices.layoutManager = LinearLayoutManager(context)
        devicesListAdapter.itemClickListener = this
        binding.rvDevices.adapter = devicesListAdapter
        devicesListAdapter.addAllDevices(
            listOf(
                Device(name = "device1"),
                Device(name = "device2", state = DeviceRentalState.Rented),
                Device(name = "device3")
            )
        )
    }

    override fun onItemClick(device: Device) {
        findNavController().navigate(DevicesListFragmentDirections.actionDevicesListFragmentToDeviceDetailsFragment())
    }
}