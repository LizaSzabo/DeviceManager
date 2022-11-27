package hu.bme.aut.android.devicemanager.ui.devicemanager.editdevice

sealed class EditDeviceViewState

object Initial : EditDeviceViewState()

object Loading : EditDeviceViewState()

object EditDeviceSuccess : EditDeviceViewState()

class EditDeviceError(val errorMessage: String) : EditDeviceViewState()