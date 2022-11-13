package hu.bme.aut.android.devicemanager.login


sealed class LoginViewState

object Initial : LoginViewState()
object Loading : LoginViewState()
object LoginSuccess : LoginViewState()
class LoginFail(val message: String) : LoginViewState()