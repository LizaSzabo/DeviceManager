package hu.bme.aut.android.devicemanager.ui.devicemanager.request

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RentalRequestViewModel @Inject constructor(
    private val rentalRequestPresenter: RentalRequestPresenter
) : RainbowCakeViewModel<RentalRequestViewState>(Initial) {
}