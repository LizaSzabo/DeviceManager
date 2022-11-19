package hu.bme.aut.android.devicemanager.ui.calendar

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.DeviceManagerApp.Companion.mockDeviceData
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentCalendarBinding
import java.util.*

@AndroidEntryPoint
class CalendarFragment : RainbowCakeFragment<CalendarViewState, CalendarViewModel>() {

    private lateinit var binding: FragmentCalendarBinding
    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_calendar
    private val args: CalendarFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // binding.calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_RANGE

        setupCalendar()

        val calendar1: Calendar = Calendar.getInstance()
        calendar1.set(2022, 11, 16)

        val device = mockDeviceData.firstOrNull { it.id == args.deviceID }
        val activeRentOnDevice = device?.calendar?.activeRents
        val disableDates = mutableListOf<Calendar>()

        Log.i("activeRentOnDevice", activeRentOnDevice.toString())

        if (activeRentOnDevice != null) {
            for (activeRent in activeRentOnDevice) {
                val calendar: Calendar = Calendar.getInstance()
                activeRent.startDate?.let {
                    calendar.set(
                        activeRent.startDate.year,
                        activeRent.startDate.month,
                        it.date
                    )
                }
                disableDates.add(calendar)
            }
        }
        Log.i("activeRentOnDeviceDisabledDates", disableDates.size.toString())
        binding.calendarView.setDisabledDays(disableDates)

        /* binding.calendarView.setDateSelected(CalendarDay.today(), true)*/


    }

    override fun render(viewState: CalendarViewState) {
        //TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setupCalendar() {
        binding.calendarView.setHeaderColor(R.color.main_variant_orange)
        binding.calendarView.setHeaderLabelColor(R.color.white)
        binding.calendarView.setAllowClickWhenDisabled(false)
        binding.calendarView.setCalendarDayLayout(R.layout.layout_calendar_day)

        val selectedDates: List<Calendar> = binding.calendarView.selectedDates
    }
}