package hu.bme.aut.android.devicemanager.ui.login

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
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.userRole
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentLoginBinding
import hu.bme.aut.android.devicemanager.util.UserRole
import hu.bme.aut.android.devicemanager.util.showSnackBar

@AndroidEntryPoint
class LoginFragment : RainbowCakeFragment<LoginViewState, LoginViewModel>() {

    private lateinit var binding: FragmentLoginBinding
    override fun provideViewModel() = getViewModelFromFactory()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLoginButton()
        setupToggleButton()
        setupRegisterLink()
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            if (binding.switchLoginRoleButton.isChecked) {
                userRole = UserRole.ADMIN
                if (!isInputError()) {
                    viewModel.loginAdmin(
                        binding.userNameInput.text.toString(),
                        binding.passwordInput.text.toString()
                    )
                }
            } else {
                userRole = UserRole.USER
                if (!isInputError()) {
                    viewModel.loginUser(
                        binding.userNameInput.text.toString(),
                        binding.passwordInput.text.toString()
                    )
                }
            }
        }
    }

    private fun setupToggleButton() {
        binding.switchLoginRoleButton.setOnClickListener {
            if (!binding.switchLoginRoleButton.isChecked) {
                viewModel.switchToUserMode()
                userRole = UserRole.USER
            } else {
                viewModel.switchToAdminMode()
                userRole = UserRole.ADMIN
            }
        }
    }

    private fun setupRegisterLink() {
        binding.registrationLink.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    override fun render(viewState: LoginViewState) {
        when (viewState) {
            is InitialUser -> {
                binding.textViewAdmin.isVisible = false
                binding.registrationLink.isVisible = true
                binding.loading.isVisible = false
                binding.loginButton.isEnabled = true
                binding.switchLoginRoleButton.isEnabled = true
            }
            is InitialAdmin -> {
                binding.textViewAdmin.isVisible = true
                binding.registrationLink.isVisible = false
                binding.loading.isVisible = false
                binding.loginButton.isEnabled = true
                binding.switchLoginRoleButton.isEnabled = true
            }
            is Loading -> {
                binding.loading.isVisible = true
                binding.loginButton.isEnabled = false
                binding.switchLoginRoleButton.isEnabled = false
            }
            is LoginSuccessWithUser -> {
                binding.loading.isVisible = false
                binding.loginButton.isEnabled = true
                binding.switchLoginRoleButton.isEnabled = true
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDevicesListFragment())
            }
            is LoginSuccessWithAdmin -> {
                binding.loading.isVisible = false
                binding.loginButton.isEnabled = true
                binding.switchLoginRoleButton.isEnabled = true
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToStartMenuFragment())
            }
            is LoginFail -> {
                binding.loading.isVisible = false
                binding.loginButton.isEnabled = true
                binding.switchLoginRoleButton.isEnabled = true
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(binding.root, errorColor, viewState.message)
            }
        }
    }

    private fun isInputError(): Boolean {
        var error = false
        if (binding.userNameInput.text.isEmpty()) {
            binding.userNameInput.error = "USER name cannot be empty!"
            error = true
        }
        if (binding.passwordInput.text.isEmpty()) {
            binding.passwordInput.error = "Password cannot be empty!"
            error = true
        }
        return error
    }


}