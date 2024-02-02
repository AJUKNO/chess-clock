package nl.hva.chessclock.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.hva.chessclock.R

@Composable
fun ClockBar(
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit,
    onSettingsClick: () -> Unit,
    isStarted: Boolean
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .height(80.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClockBarItem(
                onClick = { if (isStarted) onPause() else onPlay() },
                icon = if (isStarted) painterResource(id = R.drawable.ic_pause) else Icons.Filled.PlayArrow,
                description = if (isStarted) stringResource(R.string.pause) else stringResource(R.string.play)
            )
            ClockBarItem(
                onClick = onReset,
                icon = painterResource(id = R.drawable.ic_rotate_left),
                description = stringResource(R.string.reset)
            )
            ClockBarItem(
                onClick = onSettingsClick,
                icon = painterResource(id = R.drawable.ic_timer),
                description = stringResource(R.string.settings)
            )
        }
    }
}