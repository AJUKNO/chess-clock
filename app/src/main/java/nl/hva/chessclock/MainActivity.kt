package nl.hva.chessclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.hva.chessclock.ui.screens.PlayScreen
import nl.hva.chessclock.ui.theme.ChessClockTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessClockTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChessClockApp()
                }
            }
        }
    }
}

@Composable
fun ChessClockApp() {
    PlayScreen()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChessClockTheme {
        ChessClockApp()
    }
}

/**
 * Extension function to format a duration in milliseconds.
 *
 * @return Formatted time string in the format "hh:mm:ss" if hours are present, or "mm:ss".
 */
fun Long.formatTime(): String {
    val hours = this / 3600000
    val remainingTime = this % 3600000
    val minutes = remainingTime / 60000
    val seconds = (remainingTime % 60000) / 1000

    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%d:%02d", minutes, seconds)
    }
}