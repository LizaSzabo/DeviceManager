package hu.bme.aut.android.devicemanager.ui.createadmin

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationResult
import javax.inject.Inject

@HiltViewModel
class CreateAdminViewModel @Inject constructor(
    private val createAdminPresenter: CreateAdminPresenter
) : RainbowCakeViewModel<CreateAdminViewState>(Initial) {

    fun createAdmin(userName: String, password: String) = execute {
        viewState = Loading
        viewState = when (val adminCreationResponse =
            createAdminPresenter.createAdmin(userName, password)) {
            is PresentationResult -> {
                AdminCreationSuccess
            }
            is PresentationNetworkError -> {
                if (adminCreationResponse.message != null) {
                    AdminCreationError(adminCreationResponse.message)
                } else {
                    AdminCreationError("Network error!")
                }
            }
        }
    }
}