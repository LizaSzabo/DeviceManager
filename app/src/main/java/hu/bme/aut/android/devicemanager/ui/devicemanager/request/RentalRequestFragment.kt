package hu.bme.aut.android.devicemanager.ui.devicemanager.request

import android.annotation.SuppressLint
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
    }

    override fun render(viewState: RentalRequestViewState) {
        when (viewState) {
            is Initial -> {
                binding.loading.isVisible = false
            }
            is DataLoading -> {
                binding.loading.isVisible = true
            }
            is DataReady -> {
                setupData(viewState.device)
                binding.loading.isVisible = false
            }
            is DataLoadingFailed -> {
                binding.loading.isVisible = false
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
        val startDate =  device.calendar?.activeRents?.firstOrNull{ it.userId == currentUser.id }?.startDate
        val endDate = device.calendar?.activeRents?.firstOrNull { it.userId == currentUser.id }?.endDate
        binding.deviceName.text = device.name
        binding.deviceState.text = device.state.toString()
        binding.userName.text = currentUser.userName
        if(startDate != null && endDate != null){
            binding.rentalInterval.text = "$startDate -- $endDate"
        }
    }
}