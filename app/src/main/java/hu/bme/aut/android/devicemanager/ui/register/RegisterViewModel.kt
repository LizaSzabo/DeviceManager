package hu.bme.aut.android.devicemanager.ui.register

import android.content.res.Resources
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationNoResult
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerPresenter: RegisterPresenter
) : RainbowCakeViewModel<RegisterViewState>(RegisterInitial) {

    fun createUser(userName: String, password: String) = execute {
        viewState = RegisterLoading
        viewState =
            when (val createUserResponse = registerPresenter.createUser(userName, password)) {
                is PresentationNoResult -> {
                    RegisterSuccess
                }
                else -> {
                    RegisterFail(Resources.getSystem().getString(R.string.unknown_error_text))
                }
            }
    }
}