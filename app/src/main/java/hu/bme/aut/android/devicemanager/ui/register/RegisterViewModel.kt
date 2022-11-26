package hu.bme.aut.android.devicemanager.ui.register

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationResult
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerPresenter: RegisterPresenter
) : RainbowCakeViewModel<RegisterViewState>(RegisterInitial) {

    fun createUser(userName: String, password: String) = execute {
        viewState = RegisterLoading
        val createUserResponse = registerPresenter.createUser(userName, password)
        viewState = RegisterSuccess
        viewState = when (createUserResponse) {
            is PresentationResult -> RegisterSuccess
            is PresentationNetworkError -> {
                RegisterFail(createUserResponse.message ?: "Unknown error")
            }
        }
        if (viewState is RegisterLoading) viewState = RegisterSuccess
    }
}