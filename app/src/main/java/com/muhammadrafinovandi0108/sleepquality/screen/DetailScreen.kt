package com.muhammadrafinovandi0108.sleepquality.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.muhammadrafinovandi0108.sleepquality.R
import com.muhammadrafinovandi0108.sleepquality.ui.theme.SleepQualityTheme
import com.muhammadrafinovandi0108.sleepquality.util.ViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)
    val activity = context as? androidx.appcompat.app.AppCompatActivity

    val jamTidur = rememberSaveable { mutableIntStateOf(0) }
    val menitTidur = rememberSaveable { mutableIntStateOf(0) }

    val jamBangun = rememberSaveable { mutableIntStateOf(0) }
    val menitBangun = rememberSaveable {mutableIntStateOf(0)}

    val waktuBangun = rememberSaveable { mutableStateOf("Masukan Jam") }
    val waktuTidur = rememberSaveable { mutableStateOf("Masukan Jam") }

    val radioOptions =listOf(
        stringResource(id = R.string.anak),
        stringResource(id = R.string.remaja),
        stringResource(id = R.string.dewasa)
    )
    var kelompok by rememberSaveable {mutableStateOf(radioOptions[0])}

    var statusTidur by rememberSaveable { mutableIntStateOf(0) }
    var durasiTidur by rememberSaveable { mutableIntStateOf(0) }

    var jamTidurError by remember { mutableStateOf(false) }
    var jamBangunError by remember { mutableStateOf(false) }

    val isEditMode = id != null
    var isLoaded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(id) {
        if (id == null || isLoaded) return@LaunchedEffect

        val data = viewModel.getById(id) ?: return@LaunchedEffect

        waktuTidur.value = data.jamTidur
        waktuBangun.value = data.jamBangun
        kelompok = data.kategori
        durasiTidur = data.durasi
        statusTidur = data.status

        val (jamT, menitT) = data.jamTidur.split(":")
        val (jamB, menitB) = data.jamBangun.split(":")

        jamTidur.intValue = jamT.toInt()
        menitTidur.intValue = menitT.toInt()
        jamBangun.intValue = jamB.toInt()
        menitBangun.intValue = menitB.toInt()

        isLoaded = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = if (isEditMode)
                        stringResource(R.string.edit_jam)
                        else
                        stringResource(R.string.input_jam)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.app_intro),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = waktuTidur.value,
                onValueChange = {},
                readOnly = true,
                enabled = false,
                label = { Text(text = stringResource(R.string.jam_tidur)) },
                trailingIcon = { IconPicker(isError = jamTidurError, unit = "" ) },
                supportingText = { ErrorHint(isError = jamTidurError) },
                isError = jamTidurError,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activity?.let { act ->
                            val picker =
                                MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                                    .setHour(jamTidur.intValue).setMinute(menitTidur.intValue)
                                    .setTitleText("Pilih Jam Tidur")
                                    .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK).build()

                            picker.show(act.supportFragmentManager, "TIME_PICKER")

                            picker.addOnPositiveButtonClickListener {
                                jamTidur.intValue = picker.hour
                                menitTidur.intValue = picker.minute
                                waktuTidur.value = String.format(
                                    java.util.Locale.getDefault(),
                                    "%02d:%02d",
                                    picker.hour,
                                    picker.minute
                                )
                            }
                        }

                    }
            )
            OutlinedTextField(
                value = waktuBangun.value,
                onValueChange = {},
                readOnly = true,
                enabled = false,
                label = {Text(text = stringResource(R.string.jam_bangun))},
                trailingIcon = { IconPicker(isError = jamBangunError, unit = "" ) },
                supportingText = { ErrorHint(isError = jamBangunError) },
                isError = jamBangunError,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activity?.let { act ->
                            val picker =
                                MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                                    .setHour(jamBangun.intValue).setMinute(menitBangun.intValue)
                                    .setTitleText("Pilih Jam Tidur")
                                    .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK).build()

                            picker.show(act.supportFragmentManager, "TIME_PICKER")

                            picker.addOnPositiveButtonClickListener {
                                jamBangun.intValue = picker.hour
                                menitBangun.intValue = picker.minute
                                waktuBangun.value = String.format(
                                    java.util.Locale.getDefault(),
                                    "%02d:%02d",
                                    picker.hour,
                                    picker.minute
                                )
                            }
                        }
                    }
            )
            Row (
                modifier = Modifier
                    .padding(top = 6.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            ){
                radioOptions.forEach { text ->
                    KelompokUmurOption(
                        label = text,
                        isSelected = kelompok == text,
                        modifier = Modifier
                            .selectable(
                                selected = kelompok == text,
                                onClick = { kelompok = text },
                                role = Role.RadioButton
                            )
                            .weight(1f)
                            .padding(vertical = 12.dp, horizontal = 5.dp)
                    )
                }
            }
            Button(
                onClick = {
                    jamTidurError = (waktuTidur.value == "Masukan Jam")
                    jamBangunError = (waktuBangun.value == "Masukan Jam")
                    if (jamTidurError || jamBangunError) return@Button

                    val totalMenit = hitungDurasi(
                        jamTidur.intValue, menitTidur.intValue,
                        jamBangun.intValue, menitBangun.intValue
                    )

                    durasiTidur = totalMenit
                    statusTidur = getTidurStatus(totalMenit / 60, kelompok, radioOptions)

                    if (isEditMode) {
                        viewModel.update(
                            id = id!!,
                            jamTidur = waktuTidur.value,
                            jamBangun = waktuBangun.value,
                            kategori = kelompok,
                            durasi = durasiTidur,
                            status = statusTidur
                        )
                    } else {
                        viewModel.insert(
                            jamTidur = waktuTidur.value,
                            jamBangun = waktuBangun.value,
                            kategori = kelompok,
                            durasi = durasiTidur,
                            status = statusTidur
                        )
                    }
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.hitung))
            }

            if (statusTidur !=0 ){

                val emojiResource = emoji(statusTidur)

                val jam = durasiTidur / 60
                val menit = durasiTidur % 60
                val statusTeks = stringResource(statusTidur)

                val message = stringResource(R.string.bagikan_template,
                    waktuTidur.value, waktuBangun.value, kelompok, jam, menit, statusTeks
                )

                if (emojiResource != 0) {
                    Image(
                        painter = painterResource(id = emojiResource),
                        contentDescription = stringResource(statusTidur),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .size(132.dp)
                    )
                }
                Text(
                    text = "Durasi Tidur: $jam Jam $menit Menit",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = stringResource(statusTidur).uppercase(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Button(
                    onClick = { shareData(context, message)},
                    modifier = Modifier.padding(top = 8.dp),
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                ) {
                    Text(text = stringResource(R.string.bagikan))
                }
            }
        }
    }
}


@Composable
fun KelompokUmurOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    }else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}
private fun hitungDurasi(jTidur: Int, mTidur: Int, jBangun: Int, mBangun: Int): Int {
    val totalMenitTidur = jTidur * 60 + mTidur
    var totalMenitBangun = jBangun * 60 + mBangun

    if (totalMenitBangun < totalMenitTidur ) {
        totalMenitBangun += 24 * 60
    }

    return totalMenitBangun - totalMenitTidur
}

private fun getTidurStatus (durasi: Int, kategori: String, options: List<String>): Int {
    return when (kategori) {
        options[0] -> {
            when {
                durasi < 9 -> R.string.kurang
                durasi <= 10 -> R.string.cukup
                else -> R.string.ideal
            }
        }
        options[1] -> {
            when {
                durasi < 8 -> R.string.kurang
                durasi <= 9 -> R.string.cukup
                else -> R.string.ideal
            }
        }
        else -> {
            when {
                durasi < 7 -> R.string.kurang
                durasi <= 8 -> R.string.cukup
                else -> R.string.ideal
            }
        }
    }
}

private fun emoji(statusId: Int): Int {
    return when (statusId) {
        R.string.kurang -> R.drawable.kurang
        R.string.cukup -> R.drawable.cukup
        R.string.ideal -> R.drawable.ideal
        else -> 0
    }
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    SleepQualityTheme {
        DetailScreen(rememberNavController())
    }
}