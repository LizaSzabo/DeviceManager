package hu.bme.aut.android.devicemanager.ui.devicemanager.list

import hu.bme.aut.android.devicemanager.domain.model.Device


sealed class DevicesListViewState()

object Initial : DevicesListViewState()

class DataLoading : DevicesListViewState()

class DataReady(val devices: List<Device>) : DevicesListViewState()

class NetworkError : DevicesListViewState()