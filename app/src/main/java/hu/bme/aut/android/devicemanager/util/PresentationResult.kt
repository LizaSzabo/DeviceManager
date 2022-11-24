package hu.bme.aut.android.devicemanager.util

sealed class PresentationResult

class PresentationResponse(val data: Any) : PresentationResult()

class PresentationNetworkError(val message: String) : PresentationResult()

object PresentationNoResult : PresentationResult()