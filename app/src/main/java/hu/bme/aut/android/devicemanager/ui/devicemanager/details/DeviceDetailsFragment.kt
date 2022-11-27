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
import hu.bme.aut.android.devicemanager.domain.model.Calendar
import hu.bme.aut.android.devicemanager.domain.model.Device
import hu.bme.aut.android.devicemanager.domain.model.DeviceRentalState
import hu.bme.aut.android.devicemanager.util.UserRole
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
                binding.buttonEdit.isEnabled = false
                binding.loading.isVisible = false
            }
            is DataLoading -> {
                binding.buttonRent.isEnabled = false
                binding.buttonEdit.isEnabled = false
                binding.loading.isVisible = true
            }
            is DeviceDataReady -> {
                binding.buttonRent.isEnabled = true
                binding.buttonEdit.isEnabled = true
                binding.loading.isVisible = false
                setupEditButton(viewState.device)
                showDeviceData(viewState.device)
            }
            is DeviceDataLoadingFailure -> {
                binding.buttonRent.isEnabled = false
                binding.loading.isVisible = false
                binding.buttonEdit.isEnabled = false
            }
        }
    }

    private fun showDeviceData(device: Device) {
        binding.deviceName.text = device.name
        binding.deviceState.text = device.state.toString()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val today = LocalDateTime.now().format(formatter)
        if (device.state == DeviceRentalState.Available) {
            binding.deviceFirstAvailableDate.text = today.toString()
        } else if (device.calendar != null) {
            binding.deviceFirstAvailableDate.text =
                getFirstAvailableDate(device.calendar as MutableList<Calendar>)
        } else {
            binding.deviceFirstAvailableDate.text = ""
        }

    }

    private fun setupUserRole() {
        binding.buttonEdit.isVisible = userRole == UserRole.Admin
    }

    private fun setupRentButton() {
        binding.buttonRent.setOnClickListener {
            findNavController().navigate(
                DeviceDetailsFragmentDirections.actionDeviceDetailsFragmentToRentalRequestFragment(
                    args.deviceID,
                    "",
                    ""
                )
            )
        }
    }

    private fun getFirstAvailableDate(calendarList: MutableList<Calendar>): String {
        calendarList.sortBy { it.from }
        val today = LocalDateTime.now()
        var firstAvailableDateString = ""
        for (calendar in calendarList) {
            if (calendar.to >= today.toString()) {
                val lastRentedDate = LocalDateTime.parse(calendar.to)
                val firstAvailableDate = LocalDate.of(
                    lastRentedDate.year,
                    lastRentedDate.month,
                    lastRentedDate.dayOfMonth
                ).plusDays(1)
                firstAvailableDateString = firstAvailableDate.toString()
            }
        }
        return firstAvailableDateString
    }

    private fun setupEditButton(device: Device) {
        binding.buttonEdit.setOnClickListener {
            if (device.id != null) {
                findNavController().navigate(
                    DeviceDetailsFragmentDirections.actionDeviceDetailsFragmentToEditDeviceFragment(
                        device.id,
                        device.name
                    )
                )
            }
        }
    }
}