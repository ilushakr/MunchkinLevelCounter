package com.example.munchkinlevelcounter.android.presentation.newmunchkin

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.munchkinlevelcounter.android.viewmodels.MunckinsViewModel
import com.example.munchkinlevelcounter.android.UiEvent
import com.example.munchkinlevelcounter.android.navigation.NavigationEvent
import com.example.munchkinlevelcounter.android.utils.collectNavigationEvents
import com.example.sharedmainfeature.composeColor
import com.example.sharedmainfeature.objects.Munchkin
import com.example.sharedmainfeature.objects.RGBColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateMunchkinScreen(
    viewModel: MunckinsViewModel,
    arguments: Munchkin,
    onNavigate: (NavigationEvent) -> Unit
) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var newMunchkin by remember { mutableStateOf(arguments) }
    var dialogState by remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    collectNavigationEvents(viewModel.navigationEvent) { event ->
        when (event) {
            is NavigationEvent.ShowSnackbar -> {
                coroutineScope.launch {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        actionLabel = event.action?.asString(context)
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        // Do smthg
                    }
                }

            }
            else -> onNavigate(event)
        }
    }

    LaunchedEffect(key1 = viewModel.state.collectAsState().value.data) {
        if (viewModel.state.value.data?.any { it == newMunchkin.copy(id = it.id) } == true) {
            viewModel.onEvent(UiEvent.OnBackClick)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text("Create new munchkin", color = Color.White)
                },
                backgroundColor = MaterialTheme.colors.secondary,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(UiEvent.OnBackClick)
                        }
                    ) {
                        Icon(Icons.Filled.ArrowBack, "backIcon", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            keyboardController?.hide()
                            newMunchkin = newMunchkin.copy(name = newMunchkin.name.trim())
                            viewModel.onEvent(
                                UiEvent.SaveNewMunchkin(
                                    editableMunchkin = newMunchkin,
                                    munchkin = arguments
                                )
                            )
                        }
                    ) {
                        Icon(Icons.Default.Done, contentDescription = "save", tint = Color.White)
                    }
                }
            )
        },
        content = {

            if (dialogState) {
                Dialog(
                    onDismissRequest = { dialogState = !dialogState },
                    content = {
                        ColorPickerDialog(newMunchkin) { color ->
                            dialogState = !dialogState
                            newMunchkin = newMunchkin.copy(colorRGB = color)
                        }
                    },
                    properties = DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = newMunchkin.name,
                    onValueChange = {
                        newMunchkin = newMunchkin.copy(name = it)
                    },
                    label = { Text("Name") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = MaterialTheme.colors.secondary,
                        cursorColor = MaterialTheme.colors.secondary,
                        focusedLabelColor = MaterialTheme.colors.secondary,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                ) {
                    SexChooserComponent(
                        newMunchkin = newMunchkin,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) { newSex ->
                        newMunchkin = newMunchkin.copy(sex = newSex)
                    }
                    ColorChooserComponent(
                        newMunchkin = newMunchkin,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        dialogState = !dialogState
                    }
                }
            }
        }
    )
}

@Composable
fun SexChooserComponent(
    newMunchkin: Munchkin,
    modifier: Modifier = Modifier,
    onSelect: (String) -> Unit
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Column {
            Text(
                text = "Sex", modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )

            Munchkin.getAvailableSexes().forEach { sex ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (sex == newMunchkin.sex),
                        onClick = { onSelect(sex) }
                    )
                    Icon(
                        imageVector = sex.getSexIcon(),
                        contentDescription = "sex",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

private fun String.getSexIcon() = when (this) {
    "male" -> Icons.Default.Male
    "female" -> Icons.Default.Female
    else -> Icons.Default.Transgender
}

@Composable
private fun ColorChooserComponent(
    newMunchkin: Munchkin,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Column {

            Text(
                text = "Color", modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            IconButton(
                onClick = { onClick() },
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(36.dp)
            ) {
                Canvas(
                    modifier = Modifier.fillMaxSize(),
                    onDraw = {
                        drawCircle(
                            color = newMunchkin.composeColor()
                        )
                    }
                )
            }

        }
    }
}

@Composable
fun ColorPickerDialog(
    munchkin: Munchkin,
    onSelect: (RGBColor) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(24.dp)
    ) {
        Text(text = "Select color", modifier = Modifier.padding(start = 16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(Munchkin.colorsListRGB) { color ->
                IconButton(
                    onClick = { onSelect(color) },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(36.dp)
                ) {
                    Canvas(
                        modifier = Modifier.fillMaxSize(),
                        onDraw = {
                            drawCircle(
                                color = color.composeColor()
                            )
                        }
                    )
                    if (munchkin.colorRGB == color) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "current color",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}
