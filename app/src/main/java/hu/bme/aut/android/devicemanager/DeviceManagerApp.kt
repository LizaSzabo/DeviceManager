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
import java.time.LocalDate


@HiltAndroidApp
class DeviceManagerApp : Application() {

    companion object {
        val mockDeviceData = mutableListOf<Device>(
        )
        val mockRentalRequestData = mutableListOf<RentalRequest>()
        var userRole = UserRole.User
        lateinit var currentUser : User

        var token = ""
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