package com.ircl.baubapchallenge.ui.utils

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ircl.baubapchallenge.ui.theme.BaubapChallengeTheme

@Composable
fun BasicAlertDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Ok")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewBasicAlertDialog() {
    BaubapChallengeTheme {
        BasicAlertDialog(
            title = "Dialog Title",
            message = "This is the dialog message.",
            onDismiss = { }
        )
    }
}