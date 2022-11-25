package hu.bme.aut.android.devicemanager.ui.devicemanager.adddevice

sealed class AddDeviceViewState

object Initial : AddDeviceViewState()

object SaveNewDeviceSuccess : AddDeviceViewState()

object Loading : AddDeviceViewState()

class SaveNewDeviceError(val errorMessage: String) : AddDeviceViewState()