package hu.bme.aut.android.devicemanager.ui.devicemanager.list

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.userRole
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentDevicesListBinding
import hu.bme.aut.android.devicemanager.domain.model.Device
import hu.bme.aut.android.devicemanager.util.UserRole
import hu.bme.aut.android.devicemanager.util.showSnackBar

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
        setupFloatingActionButtonVisibility()
        setupFloatingActionButton()
        viewModel.loadDevices()
    }

    override fun render(viewState: DevicesListViewState) {
        when (viewState) {
            is Initial -> {
                binding.loading.isVisible = false
                binding.noDevicesText.isVisible = false
            }
            is Loading -> {
                binding.loading.isVisible = true
                binding.noDevicesText.isVisible = false
            }
            is DataReady -> {
                binding.loading.isVisible = false
                binding.noDevicesText.isVisible = false
                devicesListAdapter.addAllDevices(viewState.devices)
                if (viewState.devices.isEmpty()) {
                    binding.noDevicesText.isVisible = true
                }
            }
            is LoadingError -> {
                binding.loading.isVisible = false
                binding.noDevicesText.isVisible = false
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(binding.root, errorColor, viewState.message)
            }
            is DeleteError -> {
                binding.loading.isVisible = false
                binding.noDevicesText.isVisible = false
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(binding.root, errorColor, viewState.message)
            }
            DeleteSuccess -> {
                binding.loading.isVisible = false
                binding.noDevicesText.isVisible = false
                val errorColor = activity?.getColor(R.color.success_color) ?: Color.GREEN
                showSnackBar(binding.root, errorColor, "Successfully deleted!")
                refreshList()
            }
        }
    }

    private fun setupRecyclerView() {
        devicesListAdapter = DevicesListAdapter()
        binding.rvDevices.layoutManager = LinearLayoutManager(context)
        devicesListAdapter.itemClickListener = this
        binding.rvDevices.adapter = devicesListAdapter
    }

    override fun onItemClick(device: Device) {
        device.id?.let {
            findNavController().navigate(
                DevicesListFragmentDirections.actionDevicesListFragmentToDeviceDetailsFragment(
                    it
                )
            )
        }
    }

    override fun onDeleteClick(device: Device) {
        showAlterDiagram(
            device.name,
            "Delete this device permanently?",
            R.style.AlertDialogTheme,
            device.id
        )
    }

    private fun setupFloatingActionButtonVisibility() {
        binding.fab.isVisible = userRole != UserRole.User
    }

    private fun setupFloatingActionButton() {
        binding.fab.setOnClickListener {
            findNavController().navigate(DevicesListFragmentDirections.actionDevicesListFragmentToAddDeviceDialogfragment())
        }
    }

    private fun showAlterDiagram(
        title: String,
        message: String,
        dialogStyleId: Int,
        deviceId: String?
    ) {
        AlertDialog.Builder(requireContext(), dialogStyleId)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { _, i ->
                deviceId?.let { viewModel.deleteDevice(it) }
            }
            .show()
    }

    private fun refreshList() {
        viewModel.loadDevices()
    }
}