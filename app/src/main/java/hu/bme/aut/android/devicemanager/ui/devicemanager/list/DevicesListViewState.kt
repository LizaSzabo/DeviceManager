package hu.bme.aut.android.devicemanager.ui.devicemanager.list

import hu.bme.aut.android.devicemanager.domain.model.Device


sealed class DevicesListViewState

object Initial : DevicesListViewState()

object Loading : DevicesListViewState()

class DataReady(val devices: List<Device>) : DevicesListViewState()

class LoadingError(val message: String) : DevicesListViewState()

class DeleteError(val message: String) : DevicesListViewState()

object DeleteSuccess : DevicesListViewState()