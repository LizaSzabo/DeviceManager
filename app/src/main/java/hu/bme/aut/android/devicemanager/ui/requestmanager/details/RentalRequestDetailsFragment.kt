package hu.bme.aut.android.devicemanager.ui.requestmanager.details

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
import hu.bme.aut.android.devicemanager.databinding.FragmentRequestDetailsBinding
import hu.bme.aut.android.devicemanager.domain.model.RentalRequest

@AndroidEntryPoint
class RentalRequestDetailsFragment :
    RainbowCakeFragment<RentalRequestDetailsViewState, RentalRequestDetailsViewModel>() {

    private lateinit var binding: FragmentRequestDetailsBinding
    override fun provideViewModel() = getViewModelFromFactory()
    private val args: RentalRequestDetailsFragmentArgs by navArgs()
    var deviceId: String? = null

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
        setupTakeBackButton()
    }

    override fun render(viewState: RentalRequestDetailsViewState) {
        when (viewState) {
            is Initial -> {
                binding.buttonAccept.isEnabled = false
                binding.buttonTakeBack.isEnabled = false
                binding.loading.isVisible = false
            }
            is RentalRequestDataLoading -> {
                binding.buttonAccept.isEnabled = false
                binding.buttonTakeBack.isEnabled = false
                binding.loading.isVisible = true
            }
            is RentalRequestDataReady -> {
                binding.loading.isVisible = false
                showRequestData(viewState.rentalRequest)
                binding.buttonTakeBack.isEnabled = false
                binding.buttonAccept.isEnabled = true
            }
            is RentalRequestAccepted -> {
                binding.loading.isVisible = false
                showRequestData(viewState.rentalRequest)
                binding.buttonTakeBack.isEnabled = true
                binding.buttonAccept.isEnabled = false
            }
            is RentalRequestClosed -> {
                binding.loading.isVisible = false
                showRequestData(viewState.rentalRequest)
                binding.buttonTakeBack.isEnabled = false
                binding.buttonAccept.isEnabled = false
            }
            is RentalRequestLoadingFailure -> {
                binding.loading.isVisible = false
                binding.buttonAccept.isEnabled = false
                binding.buttonTakeBack.isEnabled = false
            }
            is RentalRequestAcceptFailure -> {
                binding.loading.isVisible = false
                binding.buttonAccept.isEnabled = false
                binding.buttonTakeBack.isEnabled = false
            }
        }
    }

    private fun showRequestData(rentalRequest: RentalRequest) {
        binding.deviceId.text = rentalRequest.deviceId
        binding.deviceName.text = rentalRequest.deviceName
        binding.userName.text = rentalRequest.username
        binding.requestId.text = rentalRequest.id
        binding.rentalRequestStatus.text = rentalRequest.state.toString()
        binding.rentalInterval.text = buildString {
            append(rentalRequest.from)
            append(" -- ")
            append(rentalRequest.to)
        }
    }

    private fun setupAcceptButton() {
        binding.buttonAccept.setOnClickListener {
            viewModel.acceptRentalRequest()
        }
    }

    private fun setupTakeBackButton() {
        binding.buttonTakeBack.setOnClickListener {
            deviceId?.let { it1 ->
                findNavController().navigate(
                    RentalRequestDetailsFragmentDirections.actionRentalRequestDetailsFragmentToTakeBackFragment(
                        args.rentalRequestID,
                        it1
                    )
                )
            }
        }
    }
}