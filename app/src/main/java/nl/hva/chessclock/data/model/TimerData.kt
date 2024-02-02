package nl.hva.chessclock.data.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class TimerData(
    val time: MutableStateFlow<Long>,
    var job: Job? = null,
    var ruleset: MutableStateFlow<Ruleset?> = MutableStateFlow(null),
    var active: MutableStateFlow<Boolean> = MutableStateFlow(false)
) {
    fun start(viewModelScope: CoroutineScope) {
        job?.cancel()
        active.value = true
        job = viewModelScope.launch {
            while (time.value > 0) {
                delay(1000)
                time.value -= 1000
            }
            active.value = false
        }
    }

    fun pause(addRule: Boolean) {
        active.value = false
        if (addRule && ruleset.value != null) {
            time.value += ruleset.value!!.extra.times(1000)
        }
        job?.cancel()
    }

    fun reset() {
        job?.cancel()
        time.value = 60000L
        active.value = false
        ruleset.value = null

    }
}