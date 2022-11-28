package hu.bme.aut.android.devicemanager.ui.createadmin

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentCreateAdminBinding
import hu.bme.aut.android.devicemanager.util.showSnackBar


@AndroidEntryPoint
class CreateAdminFragment : RainbowCakeFragment<CreateAdminViewState, CreateAdminViewModel>() {

    private lateinit var binding: FragmentCreateAdminBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_create_admin

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAdminBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRegisterButton()
    }


    override fun render(viewState: CreateAdminViewState) {
        when (viewState) {
            is Initial -> {
                binding.loading.isVisible = false
            }
            is Loading -> {
                binding.loading.isVisible = true
            }
            is AdminCreationSuccess -> {
                binding.loading.isVisible = false
                val successMessage =
                   getString(R.string.admin_success_creation_message_text)
                val successColor = activity?.getColor(R.color.success_color) ?: Color.GREEN
                showSnackBar(binding.root, successColor, successMessage)
                findNavController().popBackStack()
            }
            is AdminCreationError -> {
                binding.loading.isVisible = false
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(binding.root, errorColor, viewState.errorMessage)
            }
        }
    }

    private fun setupRegisterButton() {
        binding.registerButton.setOnClickListener {
            register()
        }
    }

    private fun register() {
        if (!formIsValid()) {
            viewModel.createAdmin(
                binding.userNameInput.text.toString(),
                binding.passwordInput.text.toString()
            )
        }
    }

    private fun formIsValid(): Boolean {
        var error = false
        if (binding.userNameInput.text.isEmpty()) {
            binding.userNameInput.error = "USER name cannot be empty!"
            error = true
        }
        if (binding.passwordInput.text.isEmpty()) {
            binding.passwordInput.error = "Password cannot be empty!"
            error = true
        }
        if (binding.passwordRepeatInput.text.isEmpty()) {
            binding.passwordRepeatInput.error = "Password cannot be empty!"
            error = true
        }
        if (binding.passwordRepeatInput.text != binding.passwordInput.text) {
            binding.passwordRepeatInput.error = "Passwords are not identical"
            error = true
        }
        return error
    }


}