package hu.bme.aut.android.devicemanager.ui.requestmanager.details

import hu.bme.aut.android.devicemanager.domain.model.RentalRequest

sealed class RentalRequestDetailsViewState

object Initial : RentalRequestDetailsViewState()

object RentalRequestDataLoading : RentalRequestDetailsViewState()

class RentalRequestDataReady(val rentalRequest: RentalRequest) : RentalRequestDetailsViewState()

class RentalRequestLoadingFailure(val message: String) : RentalRequestDetailsViewState()

class RentalRequestAcceptFailure(val message: String) : RentalRequestDetailsViewState()

class RentalRequestAccepted(val rentalRequest: RentalRequest) : RentalRequestDetailsViewState()