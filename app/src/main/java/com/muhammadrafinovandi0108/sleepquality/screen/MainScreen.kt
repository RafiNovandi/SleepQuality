package com.muhammadrafinovandi0108.sleepquality.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.muhammadrafinovandi0108.sleepquality.R
import com.muhammadrafinovandi0108.sleepquality.navigasi.Screen
import com.muhammadrafinovandi0108.sleepquality.ui.theme.SleepQualityTheme
import com.muhammadrafinovandi0108.sleepquality.util.SettingsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNew: Boolean
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = SettingsDataStore(context)
    val showList by dataStore.layoutFlow.collectAsState(initial = true)
    val items = listOf(

        BottomNavigationItem(
            title = stringResource(id = R.string.home),
            route = Screen.Home.route,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNew = false
        ),

        BottomNavigationItem(
            title = stringResource(id = R.string.bedtime),
            route = Screen.Bedtime.route,
            selectedIcon = Icons.Filled.Bedtime,
            unselectedIcon = Icons.Outlined.Bedtime,
            hasNew = false
        ),

        BottomNavigationItem(
            title = stringResource(id = R.string.trash),
            route = Screen.Trash.route,
            selectedIcon = Icons.Filled.Delete,
            unselectedIcon = Icons.Outlined.Delete,
            hasNew = false
        )
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when(selectedItemIndex) {
                            0 -> stringResource(R.string.app_name)
                            1 -> stringResource(R.string.riwayat_tidur)
                            else -> stringResource(R.string.sampah)
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
                actions = {
                    if (selectedItemIndex ==1) {
                        IconButton(onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                dataStore.saveLayout(!showList)
                            }
                        }) {
                            Icon(
                                painter = painterResource(
                                    if (showList)
                                        R.drawable.baseline_grid_view_24
                                    else
                                        R.drawable.baseline_view_list_24
                                ),
                                contentDescription = stringResource(
                                    if(showList) R.string.grid
                                    else R.string.list
                                ),
                                tint = Color.White
                            )
                        }
                    }
                    if (selectedItemIndex == 0) {

                        IconButton(onClick = {
                            navController.navigate(Screen.About.route)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = stringResource(R.string.tentang_aplikasi),
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            //navController.navigate(item.title)
                                  },
                        label = {
                            Text(text = item.title)
                        },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (item.hasNew){
                                        Badge ()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector =if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else {
                                        item.unselectedIcon
                                    },
                                    contentDescription = item.title
                                )
                            }


                        }
                    )
                }

            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            when(selectedItemIndex) {

                0 -> {
                    HomeContent(navController)
                }

                1 -> {
                    BedtimeScreen(navController = navController)
                }

                2 -> {
                    TrashScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
fun HomeContent(navController: NavHostController) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Button(
            onClick = {
                navController.navigate(Screen.FormBaru.route)
            }
        ) {
            Text("Tambah Data Tidur")
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    SleepQualityTheme {
        MainScreen(rememberNavController())
    }
}