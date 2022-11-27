package hu.bme.aut.android.devicemanager.ui.devicemanager.editdevice

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import co.zsmb.rainbowcake.base.RainbowCakeDialogFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.DialogEditDeviceNameBinding
import hu.bme.aut.android.devicemanager.util.showSnackBar

@AndroidEntryPoint
class EditDeviceFragment : RainbowCakeDialogFragment<EditDeviceViewState, EditDeviceViewModel>() {

    override fun provideViewModel() = getViewModelFromFactory()
    private lateinit var binding: DialogEditDeviceNameBinding
    private val args: EditDeviceFragmentArgs by navArgs()

    companion object {
        var editListener: EditListener? = null
    }

    interface EditListener {
        fun onSuccessfulEdit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogEditDeviceNameBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupHintText(args.deviceName)
        setupSaveButton()
        setupCancelButton()
    }


    override fun render(viewState: EditDeviceViewState) {
        when (viewState) {
            Initial -> {
                binding.loading.isVisible = false
            }
            Loading -> {
                binding.loading.isVisible = true
            }
            EditDeviceSuccess -> {
                binding.loading.isVisible = false
                editListener?.onSuccessfulEdit()
                dismiss()
            }
            is EditDeviceError -> {
                binding.loading.isVisible = false
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(binding.root, errorColor, viewState.errorMessage)
            }
        }
    }


    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            if (binding.editTextDeviceName.text?.isEmpty() == true) {
                binding.etDeviceName.error =
                    getString(R.string.device_name_empty_error_message_text)
            } else {
                viewModel.editDeviceName(args.deviceID, binding.editTextDeviceName.text.toString())
            }
        }
    }

    private fun setupCancelButton() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setupHintText(hintName: String) {
        binding.editTextDeviceName.hint = hintName
        binding.editTextDeviceName.setHintTextColor(R.color.background_grey_dark_orange)
        binding.editTextDeviceName.setBackgroundColor(Color.TRANSPARENT)
    }

}