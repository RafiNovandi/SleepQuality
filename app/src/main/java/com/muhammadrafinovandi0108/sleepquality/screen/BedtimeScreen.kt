package com.muhammadrafinovandi0108.sleepquality.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.muhammadrafinovandi0108.sleepquality.R
import com.muhammadrafinovandi0108.sleepquality.model.DataTidur
import com.muhammadrafinovandi0108.sleepquality.navigasi.Screen
import com.muhammadrafinovandi0108.sleepquality.ui.theme.SleepQualityTheme
import com.muhammadrafinovandi0108.sleepquality.util.SettingsDataStore
import com.muhammadrafinovandi0108.sleepquality.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BedtimeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = SettingsDataStore(context)
    val showList by dataStore.layoutFlow.collectAsState(initial = true)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaru.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.input_jam),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { innerPadding ->
        ScreenContent(modifier = Modifier.fillMaxSize().padding(innerPadding), navController = navController, showList = showList)
    }
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    showList: Boolean
    ) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val data by viewModel.dataTidur.collectAsState()

    if (data.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.list_kosong))
        }

    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(data) { item ->
                    ListItem(
                        dataTidur = item,
                        onClick = {
                            navController.navigate(Screen.FormEdit.withId(item.id))
                        }
                    )
                }
            }
        } else {
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp)
            ) {
                items(data) { item ->
                    GridItem(
                        dataTidur = item,
                        onClick = {
                            navController.navigate(Screen.FormEdit.withId(item.id))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ListItem(dataTidur: DataTidur, onClick: () -> Unit) {
    val jam = dataTidur.durasi / 60
    val menit = dataTidur.durasi % 60

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = dataTidur.tanggal,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Durasi Tidur: $jam Jam $menit Menit"
            )
            Text(
                text = stringResource(id = dataTidur.status),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun GridItem(dataTidur: DataTidur, onClick: () -> Unit) {
    val jam = dataTidur.durasi / 60
    val menit = dataTidur.durasi % 60
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = dataTidur.tanggal,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Durasi Tidur: $jam Jam $menit Menit"
            )
            Text(
                text = stringResource(id = dataTidur.status),
                color = MaterialTheme.colorScheme.primary

            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun BedtimeScreenPreview() {
    SleepQualityTheme {
    }
}