package nl.hva.chessclock.ui.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import nl.hva.chessclock.data.model.Ruleset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RulesetDropdown(
    onSelect: (Ruleset) -> Unit
) {
    val rules = Ruleset.all()
    var expanded by remember { mutableStateOf(false) }
    var selectedRule by remember { mutableStateOf(rules[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        OutlinedTextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedRule.label,
            onValueChange = {},
            label = { Text("Rule") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            rules.forEach { rule ->
                DropdownMenuItem(
                    text = { Text(rule.label) },
                    onClick = {
                        selectedRule = rule
                        expanded = false
                        onSelect(rule)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}