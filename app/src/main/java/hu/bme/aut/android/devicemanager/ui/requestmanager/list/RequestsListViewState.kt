package hu.bme.aut.android.devicemanager.ui.requestmanager.list

import hu.bme.aut.android.devicemanager.domain.model.RentalRequest

sealed class RequestsListViewState

object Initial : RequestsListViewState()

object RentalRequestsLoading : RequestsListViewState()

class RentalRequestsReady(val requests: List<RentalRequest>) : RequestsListViewState()

