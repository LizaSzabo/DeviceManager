package hu.bme.aut.android.devicemanager.ui.calendar

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.hilt.getViewModelFromFactory
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.FragmentCalendarBinding
import hu.bme.aut.android.devicemanager.domain.model.ActiveRent
import hu.bme.aut.android.devicemanager.util.showSnackBar
import java.time.LocalDate
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

        setupCalendar()
        /*  val activeRentOnDevice = device?.calendar?.activeRents
          val disableDates = mutableListOf<Calendar>()

          Log.i("activeRentOnDevice", activeRentOnDevice.toString())

          if (activeRentOnDevice != null) {
              for (activeRent in activeRentOnDevice) {
                  if (activeRent.startDate != null && activeRent.endDate != null) {
                      for (activeRentDate in activeRent.startDate..activeRent.endDate) {
                          val calendar: Calendar = Calendar.getInstance()
                          calendar.set(
                              activeRentDate.year,
                              activeRentDate.monthValue,
                              activeRentDate.dayOfMonth
                          )
                          disableDates.add(calendar)
                      }
                  }
              }
          }
          Log.i("activeRentOnDeviceDisabledDates", disableDates.size.toString())
          binding.calendarView.setDisabledDays(disableDates)*/

        /* binding.calendarView.setDateSelected(CalendarDay.today(), true)*/

        viewModel.getActiveRents(args.deviceID)
        setupSelectButton()
    }

    override fun render(viewState: CalendarViewState) {
        when (viewState) {
            is Initial -> {
                binding.loading.isVisible = false
            }
            is Loading -> {
                binding.loading.isVisible = true
            }
            is DataReady -> {
                binding.loading.isVisible = false
                setupDisableDates(viewState.activeRents)
            }
            is DataLoadingError -> {
                binding.loading.isVisible = false
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(binding.root, errorColor, viewState.errorMessage)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setupCalendar() {
        binding.calendarView.setHeaderColor(R.color.main_variant_orange)
        binding.calendarView.setHeaderLabelColor(R.color.white)
        binding.calendarView.setAllowClickWhenDisabled(false)
        binding.calendarView.setCalendarDayLayout(R.layout.layout_calendar_day)
    }

    private fun setupSelectButton() {
        binding.selectDateButton.setOnClickListener {
            val selectedDates: List<Calendar> = binding.calendarView.selectedDates
            if (selectedDates.isNotEmpty()) {
                val startDay = LocalDate.of(
                    binding.calendarView.firstSelectedDate.get(Calendar.YEAR),
                    binding.calendarView.firstSelectedDate.get(Calendar.MONTH) + 1,
                    binding.calendarView.firstSelectedDate.get(Calendar.DAY_OF_MONTH)
                )
                val endDay = LocalDate.of(
                    selectedDates.last().get(Calendar.YEAR),
                    selectedDates.last().get(Calendar.MONTH) + 1,
                    selectedDates.last().get(Calendar.DAY_OF_MONTH)
                )
                findNavController().navigate(
                    CalendarFragmentDirections.actionCalendarFragmentToRentalRequestFragment(
                        args.deviceID,
                        startDay.toString(),
                        endDay.toString(),
                    )
                )
            } else {
                val errorColor = activity?.getColor(R.color.error_color) ?: Color.RED
                showSnackBar(
                    binding.root,
                    errorColor,
                    getString(R.string.select_dates_error_messsage_text)
                )
            }
        }
    }

    private fun setupDisableDates(activeRentsOnDevice: List<ActiveRent>) {
        val disableDates = mutableListOf<Calendar>()
        for (activeRent in activeRentsOnDevice) {
            for (activeRentDate in activeRent.startDate..activeRent.endDate) {
                val calendar: Calendar = Calendar.getInstance()
                calendar.set(
                    activeRentDate.year,
                    activeRentDate.monthValue - 1,
                    activeRentDate.dayOfMonth
                )
                disableDates.add(calendar)
            }
        }

        binding.calendarView.setDisabledDays(disableDates)
    }

    operator fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> {
        return object : Iterator<LocalDate> {
            private var next = this@iterator.start
            private val finalElement = this@iterator.endInclusive
            private var hasNext = !next.isAfter(this@iterator.endInclusive)
            override fun hasNext(): Boolean = hasNext
            override fun next(): LocalDate {
                val value = next
                if (value == finalElement) {
                    hasNext = false
                } else {
                    next = next.plusDays(1)
                }
                return value
            }
        }

    }
}