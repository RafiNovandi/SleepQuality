package com.muhammadrafinovandi0108.sleepquality.screen

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.muhammadrafinovandi0108.sleepquality.R
import com.muhammadrafinovandi0108.sleepquality.ui.theme.SleepQualityTheme


enum class DeleteMode {
    SOFT_DELETE,
    HARD_DELETE
}
@Composable
fun DisplayAlertDialog(
    mode: DeleteMode,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    val pesan = when(mode) {
        DeleteMode.SOFT_DELETE ->
            stringResource(R.string.pesan_hapus)

        DeleteMode.HARD_DELETE ->
            stringResource(R.string.pesan_hapus_permanen)
    }

    val tombol = when(mode) {
        DeleteMode.SOFT_DELETE ->
            stringResource(R.string.tombol_hapus)

        DeleteMode.HARD_DELETE ->
            stringResource(R.string.hapus_permanen)
    }
    AlertDialog(
        text = { Text(text = pesan) },
        confirmButton = {
            TextButton(onClick = {onConfirmation() }) {
                Text(text = tombol)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(R.string.tombol_batal))
            }
        },
        onDismissRequest = { onDismissRequest() }
    )

}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogPreview() {
    SleepQualityTheme {
        DisplayAlertDialog(
            mode = DeleteMode.SOFT_DELETE,
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}