package hu.bme.aut.android.devicemanager.ui.register

import android.util.Log
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationNoResult
import hu.bme.aut.android.devicemanager.util.PresentationResult
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerPresenter: RegisterPresenter
) : RainbowCakeViewModel<RegisterViewState>(RegisterInitial) {

    fun createUser(userName: String, password: String) = execute {
        viewState = RegisterLoading
        Log.i(
            "registerPresenter.createUser(userName, password)",
            registerPresenter.createUser(userName, password).toString()
        )
        val createUserResponse = registerPresenter.createUser(userName, password)
        viewState = RegisterSuccess
        viewState = when (createUserResponse) {
            is PresentationNetworkError -> {
                RegisterFail(createUserResponse.message ?: "Unknown error")
            }
            is PresentationNoResult -> RegisterSuccess
            is PresentationResult -> RegisterSuccess
        }
        if (viewState is RegisterLoading) viewState = RegisterSuccess
    }
}