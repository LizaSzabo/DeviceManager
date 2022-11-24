package hu.bme.aut.android.devicemanager.ui.register

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.R
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
                val successMessage =
                    activity?.getString(R.string.create_user_success_message_text) ?: ""
                val successColor = activity?.getColor(R.color.success_color) ?: Color.GREEN
                showSnackBar(binding.root, successColor, successMessage)
                findNavController().popBackStack()
            }
            is RegisterFail -> {
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(binding.root, errorColor, viewState.message)
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
            viewModel.createUser(
                binding.userNameInput.text.toString(),
                binding.passwordInput.text.toString()
            )
        }
    }

    private fun formIsValid(): Boolean {
        var error = false
        if (binding.userNameInput.text.isEmpty()) {
            binding.userNameInput.error = "User name cannot be empty!"
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


    private fun showSnackBar(view: View, backgroundColor: Int, message: String) {
        val snackbar = Snackbar.make(
            view, message,
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.WHITE)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(backgroundColor)

        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.textSize = 18f
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snackbar.show()
    }


}