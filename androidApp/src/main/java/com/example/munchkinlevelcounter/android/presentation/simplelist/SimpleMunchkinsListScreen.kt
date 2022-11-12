package com.example.munchkinlevelcounter.android.presentation.simplelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.munchkinlevelcounter.android.viewmodels.MunckinsViewModel
import com.example.munchkinlevelcounter.android.UiEvent
import com.example.munchkinlevelcounter.android.navigation.NavigationEvent
import com.example.munchkinlevelcounter.android.presentation.MunchkinItem
import com.example.munchkinlevelcounter.android.utils.collectNavigationEvents
import com.example.sharedmainfeature.objects.Munchkin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SimpleMunchkinsListScreen(
    viewModel: MunckinsViewModel,
    onNavigate: (NavigationEvent) -> Unit
) {
    val state by viewModel.state.collectAsState()

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    var editMode by remember { mutableStateOf(false) }

    if (editMode && state.data?.isNotEmpty() != true) {
        editMode = false
    }

    collectNavigationEvents(viewModel.navigationEvent) { event ->
        when (event) {
            is NavigationEvent.Navigate -> onNavigate(event)
            is NavigationEvent.ShowSnackbar -> {
                coroutineScope.launch {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        actionLabel = event.action?.asString(context)
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        event.onAction?.invoke()
                    }
                }
            }
            else -> Unit
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text("Munchkins", color = Color.White)
                },
                backgroundColor = MaterialTheme.colors.secondary,
                actions = {
                    Actions(
                        editMode = editMode,
                        isEmptyData = state.data?.isNotEmpty() == true,
                        canRestore = state.data?.canRestore() == true,
                        viewModel = viewModel
                    ) { mode ->
                        editMode = mode
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(UiEvent.CreateMunchkinScreen(Munchkin.getEmptyMunchkin()))
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    tint = Color.White,
                    contentDescription = "add new munchkin",
                )
            }
        },
        content = {
            state.data?.let { munchkinList ->
                LazyColumn(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    itemsIndexed(munchkinList) { index, item ->
                        MunchkinItem(
                            munchkin = item,
                            editMode = editMode,
                            onDelete = { munchkin ->
                                viewModel.onEvent(UiEvent.DeleteMunchkin(munchkin))
                            }
                        ) { munchkin ->
                            val event = when (editMode) {
                                true -> UiEvent.CreateMunchkinScreen(munchkin)
                                false -> UiEvent.OpenDetailedList(munchkin)
                            }
                            viewModel.onEvent(event)
                        }

                        if (munchkinList.isNotEmpty() && index == munchkinList.size - 1) {
                            Spacer(modifier = Modifier.height(76.dp))
                        }
                    }
                }
            }

            if (state.data?.isEmpty() == true) {
                EmptyMunchkinsList()
            }
        },
    )
}

private fun List<Munchkin>?.canRestore(): Boolean {
    if (this == null) return false
    return this.any { !(it.level == 1 && it.strength == 0) }
}

@Composable
private fun Actions(
    editMode: Boolean,
    isEmptyData: Boolean,
    canRestore: Boolean,
    viewModel: MunckinsViewModel,
    onEditModeChange: (Boolean) -> Unit
) {
    when (editMode) {
        true -> EditModeActions(onEditModeChange)
        false -> NotEditModeActions(isEmptyData, canRestore, viewModel, onEditModeChange)
    }
}

@Composable
private fun EditModeActions(
    onEditModeChange: (Boolean) -> Unit
) {
    Row {
        IconButton(
            onClick = {
                onEditModeChange(false)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "done editting",
                tint = Color.White,
            )
        }
    }
}

@Composable
private fun NotEditModeActions(
    isEmptyData: Boolean,
    canRestore: Boolean,
    viewModel: MunckinsViewModel,
    onEditModeChange: (Boolean) -> Unit
) {
    Row {
        if (isEmptyData) {
            IconButton(
                enabled = canRestore,
                onClick = {
                    viewModel.onEvent(UiEvent.KillAllMunchkins)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.SettingsBackupRestore,
                    contentDescription = "restore all munchkins",
                    tint = if (canRestore) Color.White else Color.Gray,
                )
            }

            IconButton(
                onClick = {
                    onEditModeChange(true)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "edit mode",
                    tint = Color.White,
                )
            }
        }

        IconButton(
            onClick = {
                viewModel.onEvent(UiEvent.SettingsScreen)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "settings",
                tint = Color.White,
            )
        }
    }
}

@Composable
private fun EmptyMunchkinsList() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Default.SentimentDissatisfied,
            contentDescription = "empty munchkin list",
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = "No munchkins yet"
        )
    }
}