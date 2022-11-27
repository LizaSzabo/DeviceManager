package hu.bme.aut.android.devicemanager.ui.requestmanager.details

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentRequestDetailsBinding
import hu.bme.aut.android.devicemanager.domain.model.RentalRequest
import hu.bme.aut.android.devicemanager.util.showSnackBar

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
    }

    override fun render(viewState: RentalRequestDetailsViewState) {
        when (viewState) {
            is Initial -> {
                binding.buttonAccept.isEnabled = false
                binding.buttonTakeBack.isEnabled = false
                binding.loading.isVisible = false
                binding.commentLabelText.isVisible = true
                binding.commentText.isVisible = true
            }
            is RentalRequestDataLoading -> {
                binding.buttonAccept.isEnabled = false
                binding.buttonTakeBack.isEnabled = false
                binding.loading.isVisible = true
                binding.commentLabelText.isVisible = true
                binding.commentText.isVisible = true
            }
            is RentalRequestDataReady -> {
                binding.loading.isVisible = false
                showRequestData(viewState.rentalRequest)
                binding.buttonTakeBack.isEnabled = false
                binding.buttonAccept.isEnabled = true
                setupAcceptButton(viewState.rentalRequest)
                binding.commentLabelText.isVisible = true
                binding.commentText.isVisible = true
            }
            is RentalRequestAccepted -> {
                binding.loading.isVisible = false
                showRequestData(viewState.rentalRequest)
                binding.buttonTakeBack.isEnabled = true
                binding.buttonAccept.isEnabled = false
                binding.commentLabelText.isVisible = true
                binding.commentText.isVisible = true
                setupTakeBackButton(viewState.rentalRequest)
            }
            is RentalRequestClosed -> {
                binding.loading.isVisible = false
                showRequestData(viewState.rentalRequest)
                binding.buttonTakeBack.isEnabled = false
                binding.buttonAccept.isEnabled = false
                binding.commentLabelText.isVisible = false
                binding.commentText.isVisible = false
            }
            is RentalRequestLoadingFailure -> {
                binding.loading.isVisible = false
                binding.buttonAccept.isEnabled = false
                binding.buttonTakeBack.isEnabled = false
                binding.commentLabelText.isVisible = true
                binding.commentText.isVisible = true
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(binding.root, errorColor, viewState.message)
            }
            is RentalRequestAcceptFailure -> {
                binding.loading.isVisible = false
                binding.buttonAccept.isEnabled = false
                binding.buttonTakeBack.isEnabled = false
                binding.commentLabelText.isVisible = true
                binding.commentText.isVisible = true
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(binding.root, errorColor, viewState.message)
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

    private fun setupAcceptButton(rentalRequest: RentalRequest) {
        binding.buttonAccept.setOnClickListener {
            viewModel.acceptRentalRequest(
                comment = binding.commentText.text.toString(),
                rentalRequest = rentalRequest
            )
        }
    }

    private fun setupTakeBackButton(rentalRequest: RentalRequest) {
        binding.buttonTakeBack.setOnClickListener {
            if (rentalRequest.deviceId != null) {
                findNavController().navigate(
                    RentalRequestDetailsFragmentDirections.actionRentalRequestDetailsFragmentToTakeBackFragment(
                        rentalRequest.id,
                        rentalRequest.deviceId,
                        binding.commentText.text.toString()
                    )
                )
            }
        }
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            RentalRequestDetailsViewModel.RentalRequestSuccessfullyAccepted -> {
                val successColor = activity?.getColor(R.color.success_color) ?: Color.GREEN
                showSnackBar(
                    binding.root,
                    successColor,
                    getString(R.string.rental_request_successfully_accepted_message_text)
                )
            }
        }
    }
}