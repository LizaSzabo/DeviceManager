package hu.bme.aut.android.devicemanager.ui.devicemanager.request

import android.annotation.SuppressLint
import android.graphics.Color
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
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.currentUser
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentRentalRequestBinding
import hu.bme.aut.android.devicemanager.domain.model.Device
import hu.bme.aut.android.devicemanager.util.showSnackBar

@AndroidEntryPoint
class RentalRequestFragment :
    RainbowCakeFragment<RentalRequestViewState, RentalRequestViewModel>() {

    private lateinit var binding: FragmentRentalRequestBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_rental_request
    private val args: RentalRequestFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRentalRequestBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRentalIntervalTv()
        viewModel.loadData(args.deviceID)
        setupRentButton()
    }

    override fun render(viewState: RentalRequestViewState) {
        when (viewState) {
            is Initial -> {
                binding.loading.isVisible = false
                binding.buttonRent.isEnabled = false
            }
            is Loading -> {
                binding.loading.isVisible = true
                binding.buttonRent.isEnabled = false
            }
            is DataReady -> {
                setupData(viewState.device)
                binding.loading.isVisible = false
                binding.buttonRent.isEnabled = true
            }
            is DataLoadingFailed -> {
                binding.loading.isVisible = false
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(binding.root, errorColor, viewState.errorMessage)
                binding.buttonRent.isEnabled = false
            }
            is RentalRequestSaveFailed -> {
                binding.loading.isVisible = false
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(binding.root, errorColor, viewState.errorMessage)
                binding.buttonRent.isEnabled = false
            }
            RentalRequestSaveSuccess -> {
                binding.loading.isVisible = false
                binding.buttonRent.isEnabled = false
                val errorColor = activity?.getColor(R.color.success_color) ?: Color.GREEN
                showSnackBar(binding.root, errorColor, "Successfully rented!")
            }
        }
    }

    private fun setupRentalIntervalTv() {
        binding.rentalInterval.setOnClickListener {
            findNavController().navigate(
                RentalRequestFragmentDirections.actionRentalRequestFragmentToCalendarFragment(
                    args.deviceID
                )
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupData(device: Device) {
        val startDate = args.selectionStart
        val endDate = args.selectionEnd
        binding.deviceName.text = device.name
        binding.deviceState.text = device.state.toString()
        binding.userName.text = currentUser.userName
        if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
            binding.rentalInterval.text = "$startDate -- $endDate"
        } else {
            binding.rentalInterval.text = getString(R.string.click_here_for_calendar_text)
        }
    }


    private fun setupRentButton() {
        binding.buttonRent.setOnClickListener {
            val startDate = args.selectionStart
            val endDate = args.selectionEnd
            if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                viewModel.saveRentalRequest(args.deviceID, startDate, endDate)
            }
        }
    }
}