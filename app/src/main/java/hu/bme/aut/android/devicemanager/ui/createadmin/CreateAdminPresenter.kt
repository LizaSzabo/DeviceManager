package hu.bme.aut.android.devicemanager.ui.createadmin

import hu.bme.aut.android.devicemanager.domain.interactors.AuthenticationInteractor
import hu.bme.aut.android.devicemanager.util.PresentationResponse
import hu.bme.aut.android.devicemanager.util.makeNetworkCall
import javax.inject.Inject

class CreateAdminPresenter @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor
) {
    suspend fun createAdmin(userName: String, password: String): PresentationResponse<Unit> =
        makeNetworkCall(
            interactor = { authenticationInteractor.createAdmin(userName, password) },
            converter = { it }
        )
}