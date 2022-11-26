package hu.bme.aut.android.devicemanager.ui.createadmin

sealed class CreateAdminViewState

object Initial : CreateAdminViewState()

object AdminCreationSuccess : CreateAdminViewState()

object Loading : CreateAdminViewState()

class AdminCreationError(val errorMessage: String) : CreateAdminViewState()