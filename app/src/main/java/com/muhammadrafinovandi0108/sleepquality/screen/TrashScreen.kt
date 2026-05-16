package com.muhammadrafinovandi0108.sleepquality.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(item.tanggal)
                Text("Durasi: ${item.durasi}")
                Text(stringResource(id = item.status))
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(item.tanggal, fontWeight = FontWeight.Bold)
                    Text("Durasi: ${item.durasi}")
                    Text(stringResource(id = item.status))
                }
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