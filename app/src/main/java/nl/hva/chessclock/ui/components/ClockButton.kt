package nl.hva.chessclock.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import nl.hva.chessclock.data.model.Ruleset

@Composable
fun ClockButton(
    active: Boolean,
    time: Long,
    ruleset: Ruleset,
    reversed: Boolean,
    label: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val background = when {
        time == 0L -> MaterialTheme.colorScheme.error
        active -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.secondary
    }

    Box(
        modifier = modifier
            .background(background)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .let {
                    if (reversed) it.rotate(180F) else it
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.displayLarge,
                fontSize = if (label.length > 5) 4*20.sp else 4*29.sp,
                fontWeight = FontWeight.Bold,
                color = if (active) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
            )
            Text(
                text = "+ ${ruleset.extra} sec",
                style = MaterialTheme.typography.titleMedium,
                color = if (active) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
            )
        }
    }
}