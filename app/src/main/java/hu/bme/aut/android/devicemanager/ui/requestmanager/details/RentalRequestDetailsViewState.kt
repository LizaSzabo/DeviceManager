package hu.bme.aut.android.devicemanager.ui.requestmanager.details

sealed class RentalRequestDetailsViewState

object Initial : RentalRequestDetailsViewState()

object RentalRequestAccepted : RentalRequestDetailsViewState()