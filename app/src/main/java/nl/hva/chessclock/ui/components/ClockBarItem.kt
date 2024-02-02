package nl.hva.chessclock.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun <T> ClockBarItem(
    onClick: () -> Unit,
    icon: T,
    description: String
) {
    if (icon is Painter || icon is ImageVector) {
        IconButton(onClick = onClick) {
            when (icon) {
                is Painter ->
                    Icon(painter = icon, contentDescription = description, modifier = Modifier.size(36.dp))
                is ImageVector ->
                    Icon(imageVector = icon, contentDescription = description, modifier = Modifier.size(36.dp))
            }
        }
    }
}
