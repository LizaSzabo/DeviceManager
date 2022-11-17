package hu.bme.aut.android.devicemanager.ui.calendar

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarPresenter: CalendarPresenter
) : RainbowCakeViewModel<CalendarViewState>(Initial) {
}