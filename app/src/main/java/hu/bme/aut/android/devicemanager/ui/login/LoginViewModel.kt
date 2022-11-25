package hu.bme.aut.android.devicemanager.ui.login

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationNoResult
import hu.bme.aut.android.devicemanager.util.PresentationResult
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginPresenter: LoginPresenter
) : RainbowCakeViewModel<LoginViewState>(InitialUser) {

    fun switchToUserMode() {
        viewState = InitialUser
    }

    fun switchToAdminMode() {
        viewState = InitialAdmin
    }

    fun loginUser(userName: String, password: String) = execute {
        viewState = Loading
        viewState = when (val loginResponse = loginPresenter.loginUser(userName, password)) {
            is PresentationNetworkError -> {
                if (loginResponse.message != null) {
                    LoginFail(loginResponse.message)
                } else {
                    LoginFail("Network Error!")
                }
            }
            is PresentationNoResult -> {
                LoginSuccessWithUser
            }
            is PresentationResult -> {
                LoginSuccessWithUser
            }
        }
    }

    fun loginAdmin(userName: String, password: String) = execute {
        viewState = Loading
        viewState = when (val loginResponse = loginPresenter.loginUser(userName, password)) {
            is PresentationNetworkError -> {
                if (loginResponse.message != null) {
                    LoginFail(loginResponse.message)
                } else {
                    LoginFail("Network Error!")
                }
            }
            is PresentationNoResult -> {
                LoginSuccessWithAdmin
            }
            is PresentationResult -> {
                LoginSuccessWithAdmin
            }
        }
    }

}