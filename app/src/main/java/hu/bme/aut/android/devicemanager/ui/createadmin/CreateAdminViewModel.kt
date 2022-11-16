package hu.bme.aut.android.devicemanager.ui.createadmin

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateAdminViewModel @Inject constructor(
    private val createAdminPresenter: CreateAdminPresenter
) : RainbowCakeViewModel<CreateAdminViewState>(Initial) {
}