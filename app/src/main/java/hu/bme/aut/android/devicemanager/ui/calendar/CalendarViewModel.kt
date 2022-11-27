package hu.bme.aut.android.devicemanager.ui.calendar

import android.util.Log
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.devicemanager.util.PresentationNetworkError
import hu.bme.aut.android.devicemanager.util.PresentationResult
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarPresenter: CalendarPresenter
) : RainbowCakeViewModel<CalendarViewState>(Initial) {

    fun getActiveRents(deviceId: String) = execute {
        viewState = Loading

        viewState = when (val deviceData = calendarPresenter.getActiveRents(deviceId)) {
            is PresentationResult -> {
                DataReady(deviceData.result)
            }
            is PresentationNetworkError -> {
                if (deviceData.message != null) {
                    DataLoadingError(deviceData.message)
                } else {
                    DataLoadingError("Network error!")
                }
            }
        }
    }
}