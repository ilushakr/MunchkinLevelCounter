package com.example.munchkinlevelcounter.android.presentation.detailedlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.munchkinlevelcounter.android.viewmodels.MunckinsViewModel
import com.example.munchkinlevelcounter.android.R
import com.example.munchkinlevelcounter.android.UiEvent
import com.example.munchkinlevelcounter.android.navigation.NavigationEvent
import com.example.munchkinlevelcounter.android.presentation.MunchkinItem
import com.example.munchkinlevelcounter.android.presentation.getSexIcon
import com.example.munchkinlevelcounter.android.utils.collectNavigationEvents
import com.example.sharedmainfeature.objects.Munchkin
import com.example.sharedmainfeature.presentation.MunchkinStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Integer.MIN_VALUE
import kotlin.Int.Companion.MAX_VALUE

@Composable
fun DetailedMunchkinsListScreen(
    viewModel: MunckinsViewModel,
    arguments: Munchkin,
    onNavigate: (NavigationEvent) -> Unit
) {
    val state by viewModel.state.collectAsState()

    var selectedMunchkinId by remember { mutableStateOf(arguments.id) }
    val selectedMunchkin = state.getCurrentMunchkin(selectedMunchkinId)
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    collectNavigationEvents(viewModel.navigationEvent) { event ->
        when (event) {
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
            else -> onNavigate(event)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
//                    Text(text = stringResource(id = R.string.killed_munchkin, "my"))
                    Text(selectedMunchkin.name, color = Color.White)
                },
                backgroundColor = MaterialTheme.colors.secondary,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(UiEvent.OnBackClick)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "backIcon",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    Row {
                        IconButton(
                            enabled = selectedMunchkin.totalStrength() != 1,
                            onClick = {
                                viewModel.onEvent(UiEvent.KillMunchkin(selectedMunchkin))
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(R.drawable.skull_fill1_wght300_grad0_opsz48),
                                tint = if (selectedMunchkin.totalStrength() != 1) Color.White else Color.Gray,
                                contentDescription = "kill munchkin"
                            )
                        }
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Strength",
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = selectedMunchkin.totalStrength().toString(),
                        fontSize = 56.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    IconButton(
                        onClick = {
                            viewModel.updateMunchkin(
                                selectedMunchkin.copy(
                                    sex = toggleSex(selectedMunchkin)
                                )
                            )
                        }
                    ) {
                        Icon(
                            imageVector = selectedMunchkin.getSexIcon(),
                            modifier = Modifier.size(32.dp),
                            contentDescription = "sex"
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {

                        MunchkinCharacteristic(
                            scope = this,
                            characteristicName = "Level",
                            characteristicValue = selectedMunchkin.level,
                            lowerBound = 1,
                            upperBound = 10,
                            modifier = Modifier.weight(1f)
                        ) { value ->
                            viewModel.updateMunchkin(
                                selectedMunchkin.copy(
                                    level = value
                                )
                            )
                        }

                        MunchkinCharacteristic(
                            this,
                            "Strength",
                            selectedMunchkin.strength,
                            Modifier.weight(1f)
                        ) { value ->
                            viewModel.updateMunchkin(
                                selectedMunchkin.copy(
                                    strength = value
                                )
                            )
                        }

                    }
                }

                Surface(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .weight(1f),
                    elevation = 16.dp
                ) {

                    LazyColumn {
                        state.data?.let { munchkinList ->
                            itemsIndexed(munchkinList) { index, item ->
                                MunchkinItem(
                                    item,
                                    selected = selectedMunchkin == item
                                ) { munchkin ->
                                    selectedMunchkinId = munchkin.id
                                }
                            }
                        }
                    }
                }

            }
        },
    )
}

@Composable
fun MunchkinCharacteristic(
    scope: RowScope,
    characteristicName: String,
    characteristicValue: Int,
    modifier: Modifier = Modifier,
    lowerBound: Int = MIN_VALUE,
    upperBound: Int = MAX_VALUE,
    onClick: (Int) -> Unit
) = with(scope) {
    Column(
        modifier = modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = characteristicName,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = characteristicValue.toString(),
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(space = 24.dp)
        ) {
            IconButton(
                onClick = {
                    onClick(characteristicValue - 1)
                },
                enabled = lowerBound < characteristicValue
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "$characteristicName down",
                    modifier = Modifier.size(48.dp),
                    tint = if (lowerBound < characteristicValue) Color.Black else Color.Gray
                )
            }

            IconButton(
                onClick = {
                    onClick(characteristicValue + 1)
                },
                enabled = upperBound > characteristicValue
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropUp,
                    contentDescription = "$characteristicName up",
                    modifier = Modifier.size(48.dp),
                    tint = if (upperBound > characteristicValue) Color.Black else Color.Gray
                )
            }

        }
    }
}

private fun MunchkinStore.MunchkinState.getCurrentMunchkin(id: Int?) =
    this.data!!.first { it.id == id }

private fun toggleSex(munchkin: Munchkin) = when (munchkin.sex) {
    "male" -> "female"
    "female" -> "male"
    else -> "transgender"
}