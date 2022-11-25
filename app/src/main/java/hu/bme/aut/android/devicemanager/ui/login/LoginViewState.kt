package hu.bme.aut.android.devicemanager.ui.login


sealed class LoginViewState

object InitialUser : LoginViewState()
object InitialAdmin : LoginViewState()
object Loading : LoginViewState()
object LoginSuccessWithUser : LoginViewState()
object LoginSuccessWithAdmin: LoginViewState()
class LoginFail(val message: String) : LoginViewState()