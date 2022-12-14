package hu.bme.aut.android.devicemanager.ui.devicemanager.adddevice

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.DialogAddNewDeviceBinding
import hu.bme.aut.android.devicemanager.util.showSnackBar

@AndroidEntryPoint
class AddDeviceDialogFragment :
    RainbowCakeDialogFragment<AddDeviceViewState, AddDeviceViewModel>() {

    private lateinit var binding: DialogAddNewDeviceBinding
    override fun provideViewModel() = getViewModelFromFactory()

    companion object {
        var addDeviceListener: AddDeviceListener? = null
    }

    interface AddDeviceListener {
        fun onSuccessfulAddDevice()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddNewDeviceBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSaveButton()
        setupCancelButton()
    }

    override fun render(viewState: AddDeviceViewState) {
        when (viewState) {
            is Initial -> {
                binding.loading.isVisible = false
            }
            is Loading -> {
                binding.loading.isVisible = true
            }
            is SaveNewDeviceError -> {
                binding.loading.isVisible = false
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(binding.root, errorColor, viewState.errorMessage)
            }
            is SaveNewDeviceSuccess -> {
                binding.loading.isVisible = false
                addDeviceListener?.onSuccessfulAddDevice()
                dismiss()
                /* val successColor = activity?.getColor(R.color.success_color) ?: Color.GREEN
                 showSnackBar(
                     binding.root,
                     successColor,
                     getString(R.string.device_added_successfully_message_text)
                 )*/
            }
        }
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            if (binding.editTextDeviceName.text?.isEmpty() == true) {
                binding.etDeviceName.error =
                    getString(R.string.device_name_empty_error_message_text)
            } else {
                viewModel.addNewDevice(binding.editTextDeviceName.text.toString())
            }
        }
    }

    private fun setupCancelButton() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}