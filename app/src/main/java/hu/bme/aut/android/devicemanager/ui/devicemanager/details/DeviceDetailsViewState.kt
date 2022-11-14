package hu.bme.aut.android.devicemanager.ui.devicemanager.details

import hu.bme.aut.android.devicemanager.domain.model.Device

sealed class DeviceDetailsViewState

object Initial : DeviceDetailsViewState()

object DataLoading : DeviceDetailsViewState()

class DeviceDataReady(val device: Device) : DeviceDetailsViewState()

class DeviceDataLoadingFailure(val message: String) : DeviceDetailsViewState()