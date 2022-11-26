package hu.bme.aut.android.devicemanager.ui.calendar

import hu.bme.aut.android.devicemanager.domain.model.ActiveRent

sealed class CalendarViewState

object Initial : CalendarViewState()

object Loading : CalendarViewState()

class DataReady(val activeRents: List<ActiveRent>) : CalendarViewState()

class DataLoadingError(val errorMessage: String) : CalendarViewState()