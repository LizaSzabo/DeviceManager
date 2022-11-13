package hu.bme.aut.android.devicemanager.ui.register

sealed class RegisterViewState

object RegisterInitial : RegisterViewState()
object RegisterLoading : RegisterViewState()
object RegisterSuccess : RegisterViewState()
class RegisterFail(val message: String) : RegisterViewState()