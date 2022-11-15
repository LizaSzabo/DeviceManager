package hu.bme.aut.android.devicemanager.ui.requestmanager.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.databinding.FragmentRequestDetailsBinding
import hu.bme.aut.android.devicemanager.domain.model.RentalRequest

@AndroidEntryPoint
class RentalRequestDetailsFragment :
    RainbowCakeFragment<RentalRequestDetailsViewState, RentalRequestDetailsViewModel>() {

    private lateinit var binding: FragmentRequestDetailsBinding
    override fun provideViewModel() = getViewModelFromFactory()
    private val args: RentalRequestDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadRentalRequestData(args.rentalRequestID)

        setupAcceptButton()
    }

    override fun render(viewState: RentalRequestDetailsViewState) {
        when (viewState) {
            is Initial -> {
                binding.buttonAccept.isEnabled = false
                binding.loading.isVisible = false
            }
            is RentalRequestDataLoading -> {
                binding.buttonAccept.isEnabled = false
                binding.loading.isVisible = true
            }
            is RentalRequestDataReady -> {
                binding.loading.isVisible = false
                showRequestData(viewState.rentalRequest)
                binding.buttonAccept.isEnabled = true
            }
            is RentalRequestAccepted -> {
                binding.loading.isVisible = false
                showRequestData(viewState.rentalRequest)
                binding.buttonAccept.isEnabled = false
            }
            is RentalRequestLoadingFailure -> {
                binding.loading.isVisible = false
                binding.buttonAccept.isEnabled = false
            }
            is RentalRequestAcceptFailure -> {
                binding.loading.isVisible = false
                binding.buttonAccept.isEnabled = false
            }
        }
    }

    private fun showRequestData(rentalRequest: RentalRequest) {
        binding.deviceId.text = rentalRequest.device?.id.toString()
        binding.deviceName.text = rentalRequest.device?.name
        binding.userName.text = rentalRequest.user?.userName
        binding.requestId.text = rentalRequest.id.toString()
        binding.rentalRequestStatus.text = rentalRequest.status.toString()

        //TODO: delete mock data
        binding.rentalInterval.text = "2022.10.23 - 2022.12.18."
    }

    private fun setupAcceptButton() {
        binding.buttonAccept.setOnClickListener {
            viewModel.acceptRentalRequest()
        }
    }
}