package hu.bme.aut.android.devicemanager.ui.requestmanager.takeback

import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationResult
import javax.inject.Inject

@HiltViewModel
class TakeBackViewModel @Inject constructor(
    private val takeBackPresenter: TakeBackPresenter
) : RainbowCakeViewModel<TakeBackViewState>(Initial) {

    fun takeBackDevice(qrCode: String, requestId: String, deviceId: String, comment: String) =
        execute {
            if (deviceId == qrCode) {
                viewState = Loading

                viewState = when (val rentalRequestTakeBackResponse =
                    takeBackPresenter.takeBackRentalRequest(
                        rentalRequestId = requestId,
                        comment = comment
                    )) {
                    is PresentationResult -> {
                        postEvent(RentalRequestSuccessfullyClosed)
                        RequestClosedSuccessfully
                    }
                    is PresentationNetworkError -> {
                        if (rentalRequestTakeBackResponse.message != null) {
                            RequestCloseError(rentalRequestTakeBackResponse.message)
                        } else {
                            RequestCloseError("Network error!")
                        }
                    }
                }
            } else {
                viewState = NoMatchingQrCode
            }
        }

    object RentalRequestSuccessfullyClosed : OneShotEvent
}