package hu.bme.aut.android.devicemanager.ui.requestmanager.takeback

sealed class TakeBackViewState

object Initial : TakeBackViewState()

object RequestClosedSuccessfully : TakeBackViewState()

object NoMatchingQrCode : TakeBackViewState()