package hu.bme.aut.android.devicemanager.ui.devicemanager.request

import hu.bme.aut.android.devicemanager.domain.model.Device

sealed class RentalRequestViewState

object Initial : RentalRequestViewState()

object DataLoading : RentalRequestViewState()

class DataReady(val device: Device) : RentalRequestViewState()

object DataLoadingFailed : RentalRequestViewState()