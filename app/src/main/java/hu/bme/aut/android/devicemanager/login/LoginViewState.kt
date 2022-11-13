package hu.bme.aut.android.devicemanager.login


sealed class LoginViewState

object InitialUser : LoginViewState()
object InitialAdmin : LoginViewState()
object Loading : LoginViewState()
object LoginSuccess : LoginViewState()
class LoginFail(val message: String) : LoginViewState()