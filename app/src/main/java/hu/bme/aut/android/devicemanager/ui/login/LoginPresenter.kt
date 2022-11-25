package hu.bme.aut.android.devicemanager.ui.login

import hu.bme.aut.android.devicemanager.domain.interactors.AuthenticationInteractor
import hu.bme.aut.android.devicemanager.util.PresentationResponse
import hu.bme.aut.android.devicemanager.util.makeNetworkCall
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val loginInteractor: AuthenticationInteractor
) {

    suspend fun loginUser(userName: String, password: String): PresentationResponse<Any> =
        makeNetworkCall(
            interactor = { loginInteractor.loginUser(userName, password) },
            converter = { it }
        )

}