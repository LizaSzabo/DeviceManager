package hu.bme.aut.android.devicemanager.ui.requestmanager.takeback

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentTakeBackBinding
import hu.bme.aut.android.devicemanager.util.showSnackBar
import org.json.JSONException

@AndroidEntryPoint
class TakeBackFragment : RainbowCakeFragment<TakeBackViewState, TakeBackViewModel>() {

    private lateinit var binding: FragmentTakeBackBinding
    private lateinit var qrScanIntegrator: IntentIntegrator
    private val args: TakeBackFragmentArgs by navArgs()
    override fun provideViewModel() = getViewModelFromFactory()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTakeBackBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupScanner()
    }

    private fun setupScanner() {
        qrScanIntegrator = IntentIntegrator.forSupportFragment(this)
        qrScanIntegrator.setOrientationLocked(false)
        qrScanIntegrator.initiateScan()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(
                    binding.root,
                    errorColor,
                    getString(R.string.qr_code_not_found_message)
                )
                findNavController().popBackStack()
            } else {
                try {
                    result.contents
                    viewModel.takeBackDevice(
                        result.contents,
                        args.rentalRequestID,
                        args.deviceID,
                        args.comment
                    )
                } catch (e: JSONException) {
                    e.printStackTrace()
                    val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                    showSnackBar(binding.root, errorColor, result.contents)
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    override fun render(viewState: TakeBackViewState) {
        when (viewState) {
            is Initial -> {}
            is RequestClosedSuccessfully -> {
                findNavController().popBackStack()
            }
            is NoMatchingQrCode -> {
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(
                    binding.root,
                    errorColor,
                    getString(R.string.no_matching_qr_code_error_message_text)
                )
            }
            Loading -> {}
            is RequestCloseError -> {
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(
                    binding.root, errorColor, viewState.errorMessage
                )
            }
        }
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            TakeBackViewModel.RentalRequestSuccessfullyClosed -> {
                val successColor = activity?.getColor(R.color.success_color) ?: Color.GREEN
                showSnackBar(
                    binding.root,
                    successColor,
                    getString(R.string.rental_request_successfully_closed_message_text)
                )
            }
        }
    }
}