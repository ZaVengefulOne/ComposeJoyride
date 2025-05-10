package com.example.composejoyride.ui.theme

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.example.composejoyride.R

@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = true, dismissOnClickOutside = true, dismissOnBackPress = true),

        icon = {
            Icon(icon, contentDescription = "Dialog Icon")
        },
        title = {
            Text(text = dialogTitle, color = MaterialTheme.colorScheme.tertiary)
        },
        text = {
            Text(text = dialogText, color = MaterialTheme.colorScheme.tertiary)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(R.string.yes), color = MaterialTheme.colorScheme.tertiary)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.no), color = MaterialTheme.colorScheme.tertiary)
            }
        }
    )
}