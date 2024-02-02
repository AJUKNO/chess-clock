package nl.hva.chessclock.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TimerDialogInput(
    onValueChange: (Long) -> Unit,
    value: String,
    modifier: Modifier
) {
    val handleInput = { longValue: String ->
        val input = longValue.filter { char -> char.isDigit() }
        if (input.isNotEmpty() && input.toLong() <= 99L) {
            onValueChange(input.toLong())
        } else if(input.isEmpty()) {
            onValueChange(0L)
        }
    }

    OutlinedTextField(
        shape = RoundedCornerShape(8.dp),
        value = value,
        placeholder = {
            Text(
                text = "00",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        singleLine = true,
        onValueChange = {
            handleInput(it)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        textStyle = MaterialTheme.typography.titleLarge.copy(
            textAlign = TextAlign.Center
        ),
        modifier = modifier
    )
}