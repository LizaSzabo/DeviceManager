package hu.bme.aut.android.devicemanager.ui.register

import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationResult
import javax.inject.Inject

class RegisterPresenter @Inject constructor() {

    suspend fun createUser(userName: String, password: String): PresentationResult {
        return PresentationNetworkError("Not yet implemented!!!")
    }
}