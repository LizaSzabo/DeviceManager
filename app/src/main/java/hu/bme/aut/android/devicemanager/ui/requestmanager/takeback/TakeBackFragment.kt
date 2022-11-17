package hu.bme.aut.android.devicemanager.ui.requestmanager.takeback

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.databinding.FragmentTakeBackBinding
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(activity, "result not found", Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {
                    // Converting the data to json format
                    result.contents

                    // Show values in UI.
                    /*
                    binding.name.text = obj.getString("name")
                    binding.siteName.text = obj.getString("site_name")
                     */
                    viewModel.takeBackDevice(result.contents, args.rentalRequestID, args.deviceID)
                } catch (e: JSONException) {
                    e.printStackTrace()

                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
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
                Toast.makeText(activity, "successfully taken back!", Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
            is NoMatchingQrCode -> {
                Toast.makeText(
                    activity,
                    "The scanned code is not of this device",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


}