package hu.bme.aut.android.devicemanager

import android.app.Application
import co.zsmb.rainbowcake.config.Loggers
import co.zsmb.rainbowcake.config.rainbowCake
import co.zsmb.rainbowcake.timber.TIMBER
import co.zsmb.requirektx.bundle.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import hu.bme.aut.android.devicemanager.domain.model.Device
import hu.bme.aut.android.devicemanager.domain.model.DeviceRentalState
import hu.bme.aut.android.devicemanager.util.UserRole
import timber.log.Timber


@HiltAndroidApp
class DeviceManagerApp : Application() {

    companion object {
        val mockDeviceData = listOf(
            Device(id = 1, name = "device1"),
            Device(id = 2, name = "device2", state = DeviceRentalState.Rented),
            Device(id = 3, name = "device3")
        )
        var userRole = UserRole.User
    }

    override fun onCreate() {
        super.onCreate()

        setupRainbowCake()
    }

    private fun setupRainbowCake() {
        rainbowCake {
            logger = Loggers.TIMBER
            isDebug = BuildConfig.DEBUG
        }

        Timber.plant(Timber.DebugTree())
    }
}