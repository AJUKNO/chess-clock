package nl.hva.chessclock.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.hva.chessclock.data.model.Ruleset
import nl.hva.chessclock.data.model.TimerData

class ClockViewModel(application: Application) : AndroidViewModel(application) {
    // Timer data from player 1 and player 2
    private val _timer1 = TimerData(MutableStateFlow(60000L))
    private val _timer2 = TimerData(MutableStateFlow(60000L))

    val time1 = _timer1.time.asStateFlow()
    val time2 = _timer2.time.asStateFlow()

    // Clock state (STOPPED, STARTED, PAUSED)
    val clockState = MutableStateFlow(ClockState.STOPPED)

    // Properties indicating whether the timers are active
    val timer1ActiveState = _timer1.active.asStateFlow()
    val timer2ActiveState = _timer2.active.asStateFlow()

    // Reference to the last active timer (used when resuming from pause)
    private var lastActiveTimer: TimerData? = null

    /**
     * Set the initial start time for both timers
     *
     * @param minutes
     * @param seconds
     */
    private fun setStartTime(minutes: Long, seconds: Long) {
        val startTime = (minutes * 60000) + (seconds * 1000)
        _timer1.time.value = startTime
        _timer2.time.value = startTime
    }

    /**
     * Set ruleset to determine start seconds, minutes and added time
     *
     * @param ruleset
     */
    fun setRuleset(ruleset: Ruleset?) {
        if (ruleset != null) {
            resetTimers()
            setStartTime(ruleset.minutes, ruleset.seconds)
            _timer1.ruleset.value = ruleset
            _timer2.ruleset.value = ruleset
        }
    }

    /**
     * Start timer
     *
     * @param timerData
     */
    private fun startTimer(timerData: TimerData) {
        clockState.value = ClockState.STARTED
        timerData.start(viewModelScope)
        lastActiveTimer = timerData
    }

    /**
     * Start the clock based on the provided timer
     * (true for player 1, false for player 2)
     *
     * @param timer
     */
    fun startClock(timer: Boolean) {
        when (clockState.value) {
            ClockState.PAUSED -> {
                lastActiveTimer?.let { startTimer(it) }
            }
            ClockState.STOPPED -> {
                if (timer) startTimer(_timer1) else startTimer(_timer2)
            }
            ClockState.STARTED -> {
                if (timer && timer1ActiveState.value) {
                    startTimer(_timer2)
                    _timer1.pause(true)
                } else if (!timer && timer2ActiveState.value) {
                    startTimer(_timer1)
                    _timer2.pause(true)
                }
            }
        }
    }

    // Pause both timers and set the game state to PAUSED
    fun pauseClock() {
        clockState.value = ClockState.PAUSED
        _timer1.pause(false)
        _timer2.pause(false)
    }

    // Reset both timers and set the game state to STOPPED
    fun resetTimers() {
        clockState.value = ClockState.STOPPED
        _timer1.reset()
        _timer2.reset()
    }

    // Reset timers when the ViewModel is cleared
    override fun onCleared() {
        super.onCleared()
        _timer1.reset()
        _timer2.reset()
    }
}

// Clock state
enum class ClockState {
    PAUSED,
    STARTED,
    STOPPED
}