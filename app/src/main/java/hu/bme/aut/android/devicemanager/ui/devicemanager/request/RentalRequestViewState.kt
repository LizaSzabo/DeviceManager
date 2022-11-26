package hu.bme.aut.android.devicemanager.ui.devicemanager.request

import hu.bme.aut.android.devicemanager.domain.model.Device

sealed class RentalRequestViewState

object Initial : RentalRequestViewState()

object Loading : RentalRequestViewState()

class DataReady(val device: Device) : RentalRequestViewState()

object RentalRequestSaveSuccess: RentalRequestViewState()

class DataLoadingFailed(val errorMessage: String) : RentalRequestViewState()

class RentalRequestSaveFailed(val errorMessage: String) : RentalRequestViewState()