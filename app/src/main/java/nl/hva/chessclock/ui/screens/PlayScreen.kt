package nl.hva.chessclock.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.hva.chessclock.data.model.Ruleset
import nl.hva.chessclock.formatTime
import nl.hva.chessclock.ui.components.ClockBar
import nl.hva.chessclock.ui.components.ClockButton
import nl.hva.chessclock.ui.components.TimerDialog
import nl.hva.chessclock.viewmodel.ClockState
import nl.hva.chessclock.viewmodel.ClockViewModel

@Composable
fun PlayScreen() {
    val viewModel: ClockViewModel = viewModel()

    val time1Label by viewModel.time1.collectAsState()
    val time2Label by viewModel.time2.collectAsState()
    val timer1Active by viewModel.timer1ActiveState.collectAsState()
    val timer2Active by viewModel.timer2ActiveState.collectAsState()
    val clockState by viewModel.clockState.collectAsState()

    var ruleset by remember { mutableStateOf(Ruleset("Custom", 0L, 0L, 0L, true)) }

    val openAlertDialog = remember { mutableStateOf(false) }

    if (openAlertDialog.value) {
        TimerDialog(
            minutes = if (ruleset.minutes == 0L) "" else ruleset.minutes.toString(),
            seconds = if (ruleset.seconds == 0L) "" else ruleset.seconds.toString(),
            extra = if (ruleset.extra == 0L) "" else ruleset.extra.toString(),
            onDismissRequest = {
                openAlertDialog.value = false
            },
            onMinutesChange = { mins ->
                ruleset = ruleset.copy(minutes = mins)
            },
            onSecondsChange = { secs ->
                ruleset = ruleset.copy(seconds = secs)
            },
            onExtraChange = { extra ->
                ruleset = ruleset.copy(extra = extra)
            },
            onRuleSelect = { rule ->
                ruleset = rule
            },
            saveEnabled = (ruleset.minutes > 0L) || (ruleset.seconds > 0L),
            onSave = {
                openAlertDialog.value = false
                viewModel.setRuleset(ruleset)
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ClockButton(
            active = timer1Active,
            reversed = true,
            label = time1Label.formatTime(),
            time = time1Label,
            ruleset = ruleset,
            modifier = Modifier.weight(1F)
        ) {
            viewModel.startClock(true)
        }

        ClockBar(
            isStarted = clockState == ClockState.STARTED,
            onPlay = {
                viewModel.startClock(true)
            },
            onPause = {
                viewModel.pauseClock()
            },
            onReset = {
                viewModel.resetTimers()
                ruleset = Ruleset("Custom", 0L, 0L, 0L, true)
            },
            onSettingsClick = {
                openAlertDialog.value = true
            }
        )
        ClockButton(
            active = timer2Active,
            time = time2Label,
            ruleset = ruleset,
            reversed = false,
            label = time2Label.formatTime(),
            modifier = Modifier.weight(1F),
        ) {
            viewModel.startClock(false)
        }
    }
}