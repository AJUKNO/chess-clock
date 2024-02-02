package nl.hva.chessclock.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import nl.hva.chessclock.R
import nl.hva.chessclock.data.model.Ruleset


@Composable
fun TimerDialog(
    onDismissRequest: () -> Unit,
    onMinutesChange: (Long) -> Unit,
    onSecondsChange: (Long) -> Unit,
    onExtraChange: (Long) -> Unit,
    onRuleSelect: (Ruleset) -> Unit,
    onSave: () -> Unit,
    seconds: String,
    minutes: String,
    extra: String,
    saveEnabled: Boolean,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 16.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TimerDialogInput(
                        onValueChange = {
                            onMinutesChange(it)
                        },
                        value = minutes,
                        modifier = Modifier
                            .weight(1F)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        text = ":",
                        style = MaterialTheme.typography.titleLarge
                    )
                    TimerDialogInput(
                        onValueChange = {
                            onSecondsChange(it)
                        },
                        value = seconds,
                        modifier = Modifier
                            .weight(1F)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        text = "+",
                        style = MaterialTheme.typography.titleLarge
                    )
                    TimerDialogInput(
                        onValueChange = {
                            onExtraChange(it)
                        },
                        value = extra,
                        modifier = Modifier
                            .weight(1F)
                            .align(Alignment.CenterVertically)
                    )
                }

                RulesetDropdown { rule -> onRuleSelect(rule) }

                Button(
                    onClick = onSave,
                    enabled = saveEnabled,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(text = stringResource(R.string.save))
                }
            }
        }
    }
}