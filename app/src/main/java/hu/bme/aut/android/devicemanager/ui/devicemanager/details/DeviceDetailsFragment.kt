package hu.bme.aut.android.devicemanager.ui.devicemanager.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.userRole
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentDeviceDetailsBinding
import hu.bme.aut.android.devicemanager.domain.model.Device
import hu.bme.aut.android.devicemanager.util.UserRole

@AndroidEntryPoint
class DeviceDetailsFragment :
    RainbowCakeFragment<DeviceDetailsViewState, DeviceDetailsViewModel>() {

    private lateinit var binding: FragmentDeviceDetailsBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_device_details
    private val args: DeviceDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviceDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUserRole()
        setupRentButton()
        viewModel.loadDeviceData(args.deviceID)
    }

    override fun render(viewState: DeviceDetailsViewState) {
        when (viewState) {
            is Initial -> {
                binding.buttonRent.isEnabled = false
                binding.loading.isVisible = false
            }
            is DataLoading -> {
                binding.buttonRent.isEnabled = false
                binding.loading.isVisible = true
            }
            is DeviceDataReady -> {
                binding.buttonRent.isEnabled = true
                binding.loading.isVisible = false
                showDeviceData(viewState.device)
            }
            is DeviceDataLoadingFailure -> {
                binding.buttonRent.isEnabled = false
                binding.loading.isVisible = false
            }
        }
    }

    private fun showDeviceData(device: Device) {
        binding.deviceName.text = device.name
        binding.deviceState.text = device.state.toString()

        //TODO: change mock data
        binding.deviceFirstAvailableDate.text = "2023.02.15"
    }

    private fun setupUserRole() {
        if (userRole == UserRole.Admin) {
            binding.buttonEdit.isVisible = true
            binding.buttonDelete.isVisible = true
        } else {
            binding.buttonEdit.isVisible = false
            binding.buttonDelete.isVisible = false
        }
    }

    private fun setupRentButton() {
        binding.buttonRent.setOnClickListener {
            findNavController().navigate(DeviceDetailsFragmentDirections.actionDeviceDetailsFragmentToRentalRequestFragment())
        }
    }
}