package hu.bme.aut.android.devicemanager.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : RainbowCakeFragment<LoginViewState, LoginViewModel>() {

    private lateinit var binding: FragmentLoginBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_login

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
    }

    private fun setupLoginButton() {

    }

    private fun setupToggleButton(){
        binding.switchLoginRoleButton.setOnClickListener{
            if (!binding.switchLoginRoleButton.isChecked) {
                viewModel.switchToUserMode()
            } else {
                viewModel.switchToAdminMode()
            }
        }
    }

    override fun render(viewState: LoginViewState) {
        when (viewState) {
            is InitialUser -> {
                binding.textViewAdmin.isVisible = false
                binding.registrationLink.isVisible = true
            }
            is InitialAdmin -> {
                binding.textViewAdmin.isVisible = true
                binding.registrationLink.isVisible = false
            }
            is Loading -> {

            }
            is LoginSuccess -> {}
            is LoginFail -> {}
        }
    }

}