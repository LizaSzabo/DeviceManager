package hu.bme.aut.android.devicemanager.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.databinding.FragmentRegisterBinding

@AndroidEntryPoint
class RegisterFragment : RainbowCakeFragment<RegisterViewState, RegisterViewModel>() {

    private lateinit var binding: FragmentRegisterBinding
    override fun provideViewModel() = getViewModelFromFactory()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRegisterButton()
    }

    override fun render(viewState: RegisterViewState) {
        when (viewState) {
            is RegisterInitial -> {}
            is RegisterLoading -> {}
            is RegisterSuccess -> {
                /*showAlterDiagram(
                    "Registration succeeded",
                    "Go to login",
                    R.style.GreenAlertDialogTheme
                )*/
            }
            is RegisterFail -> {
                /* showAlterDiagram(
                     "Registration failed:",
                     viewState.message,
                     R.style.RedAlertDialogTheme
                 )*/
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

        }
    }

    private fun showAlterDiagram(title: String, message: String, dialogStyleId: Int) {
        AlertDialog.Builder(requireContext(), dialogStyleId)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { _, i ->
                when (dialogStyleId) {
                    /*   R.style.RedAlertDialogTheme -> {}
                       R.style.GreenAlertDialogTheme -> findNavController().popBackStack()*/
                }
            }
            .show()
    }

    private fun formIsValid(): Boolean {
        var error = false
        if (binding.userNameInput.text.isEmpty()) {
            binding.userNameInput.error = "User name cannot be empty!"
            error = true
        }
        if (binding.userEmailInput.text.isEmpty()) {
            binding.userEmailInput.error = "Password cannot be empty!"
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
        if (binding.passwordRepeatInput.text == binding.passwordInput.text) {
            binding.passwordRepeatInput.error = "Passwords are not identical"
            error = true
        }
        return error
    }


}