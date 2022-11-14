package hu.bme.aut.android.devicemanager.ui.requestmanager.details

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RentalRequestDetailsViewModel @Inject constructor(
    private val rentalRequestPresenter: RentalRequestDetailsPresenter
) : RainbowCakeViewModel<RentalRequestDetailsViewState>(Initial) {
}