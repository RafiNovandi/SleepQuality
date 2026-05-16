package com.muhammadrafinovandi0108.sleepquality.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.muhammadrafinovandi0108.sleepquality.R
import com.muhammadrafinovandi0108.sleepquality.model.DataTidur
import com.muhammadrafinovandi0108.sleepquality.util.SettingsDataStore
import com.muhammadrafinovandi0108.sleepquality.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current
    val viewModel: MainViewModel = viewModel(factory = ViewModelFactory(context))
    val dataStore = SettingsDataStore(context)
    val showList by dataStore.layoutFlow.collectAsState(initial = true)
    val data by viewModel.trashData.collectAsState()

    if (data.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.sampah_kosong))
        }
    } else {

        if (showList) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(data) { item ->
                    TrashItem(
                        item = item,
                        onRestore = { viewModel.restoreData(item.id) },
                        onDelete = { viewModel.delete(item.id) }
                    )
                }
            }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(data) { item ->
                    TrashGridItem(
                        item = item,
                        onRestore = { viewModel.restoreData(item.id) },
                        onDelete = { viewModel.delete(item.id) }
                    )
                }
            }
        }
    }
}
@Composable
fun TrashItem(
    item: DataTidur,
    onRestore: () -> Unit,
    onDelete: () -> Unit
) {
    val jam = item.durasi / 60
    val menit = item.durasi % 60

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(item.tanggal, fontWeight = FontWeight.Bold)
                Text("Durasi: $jam Jam $menit Menit")
                Text(
                    text = stringResource(id = item.status),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            TrashActionMenu(
                onRestore = onRestore,
                onDelete = onDelete
            )
        }
    }
}

@Composable
fun TrashGridItem(
    item: DataTidur,
    onRestore: () -> Unit,
    onDelete: () -> Unit
) {
    val jam = item.durasi / 60
    val menit = item.durasi % 60

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {

            // CONTENT (sama kayak Bedtime GridItem)
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = item.tanggal,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Durasi Tidur: $jam Jam $menit Menit"
                )
                Text(
                    text = stringResource(id = item.status),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                TrashActionMenu(
                    onRestore = onRestore,
                    onDelete = onDelete
                )
            }
        }
    }
}

@Composable
fun TrashActionMenu(
    onRestore: () -> Unit,
    onDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = null
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.pulihkan)) },
            onClick = {
                expanded = false
                onRestore()
            }
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.hapus_permanen)) },
            onClick = {
                expanded = false
                showDialog = true
            }
        )
    }
    if (showDialog) {
        DisplayAlertDialog(
            mode = DeleteMode.HARD_DELETE,
            onDismissRequest = { showDialog = false }
        ) {
            showDialog = false
            onDelete()
        }
    }
}