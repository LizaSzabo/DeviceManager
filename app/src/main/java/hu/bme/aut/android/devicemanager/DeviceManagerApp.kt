package hu.bme.aut.android.devicemanager

import android.app.Application
import co.zsmb.rainbowcake.config.Loggers
import co.zsmb.rainbowcake.config.rainbowCake
import co.zsmb.rainbowcake.timber.TIMBER
import co.zsmb.requirektx.bundle.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import hu.bme.aut.android.devicemanager.domain.model.*
import hu.bme.aut.android.devicemanager.util.UserRole
import timber.log.Timber


@HiltAndroidApp
class DeviceManagerApp : Application() {

    companion object {
        val mockDeviceData = mutableListOf(
            Device(id = 1, name = "device1",  qrCode = "123456"),
            Device(id = 2, name = "device2", state = DeviceRentalState.Rented),
            Device(id = 3, name = "device3")
        )
        val mockRentalRequestData = mutableListOf(
            RentalRequest(
                1,
                Device(id = 1, name = "device1",  qrCode = "123456"),
                User(1, "User Name", ""),
                RentalRequestStatus.Active
            ),
            RentalRequest(
                2,
                Device(id = 1, name = "device2"),
                User(1, "User Name", ""),
                RentalRequestStatus.Active
            ),
            RentalRequest(
                3,
                Device(id = 1, name = "device1", qrCode = "123456"),
                User(2, "User Name2", ""),
                RentalRequestStatus.Accepted
            )
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