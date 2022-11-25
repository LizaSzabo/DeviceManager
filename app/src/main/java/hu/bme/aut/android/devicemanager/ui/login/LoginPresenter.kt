package hu.bme.aut.android.devicemanager.ui.login

import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationResponse
import javax.inject.Inject

class LoginPresenter @Inject constructor(){

    suspend fun loginUser(userName: String, password: String): PresentationResponse<Unit> =
       PresentationNetworkError("Not yet implemented")

}