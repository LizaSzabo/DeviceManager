package hu.bme.aut.android.devicemanager.ui.register

import hu.bme.aut.android.devicemanager.domain.interactors.AuthenticationInteractor
import hu.bme.aut.android.devicemanager.util.PresentationResponse
import hu.bme.aut.android.devicemanager.util.makeNetworkCall
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor
) {

    suspend fun createUser(userName: String, password: String): PresentationResponse<Unit> =
        makeNetworkCall {
            authenticationInteractor.createUser(userName, password)
        }
}