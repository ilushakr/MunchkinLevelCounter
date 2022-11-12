package com.example.munchkinlevelcounter.android.presentation.settings

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.munchkinlevelcounter.android.UiEvent
import com.example.munchkinlevelcounter.android.navigation.NavigationEvent
import com.example.munchkinlevelcounter.android.utils.collectNavigationEvents
import com.example.munchkinlevelcounter.android.viewmodels.AppSettingsViewModel


@Composable
fun AppSettingsScreen(
    viewModel: AppSettingsViewModel,
    onNavigate: (NavigationEvent) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    val keepScreenOn by viewModel.keepScreenOn.collectAsState()

    collectNavigationEvents(viewModel.navigationEvent) { event ->
        when (event) {
            is NavigationEvent.PopBackStack -> onNavigate(event)
            else -> Unit
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text("Settings", color = Color.White)
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
                elevation = 16.dp
            )
        },
        content = {
            Column {
                SettingsOption(
                    modifier = Modifier.clickable {
                        viewModel.onEvent(UiEvent.ToggleScreenAlwaysOn)
                    },
                    image = if (keepScreenOn) Icons.Default.Lightbulb else Icons.Outlined.Lightbulb,
                    name = "Keep screen on"
                ) {
                    Switch(
                        checked = keepScreenOn,
                        onCheckedChange = {
                            viewModel.onEvent(UiEvent.ToggleScreenAlwaysOn)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colors.secondary
                        )
                    )
                }

                SettingsOption(
                    image = Icons.Outlined.Info,
                    name = "Version: ${viewModel.appVersion}"
                )

                SettingsOption(
                    modifier = Modifier.clickable {
//                        val url = "https://www.africau.edu/images/default/sample.pdf"
//                        val webIntent: Intent = Uri.parse(url).let { webpage ->
//                            Intent(Intent.ACTION_VIEW, webpage)
//                        }
//                        activity.startActivity(webIntent)

                        onNavigate(NavigationEvent.Navigate("webview"))
                    },
                    image = Icons.Outlined.RocketLaunch,
                    name = "Rules"
                )
            }
        }
    )
}

@Composable
fun SettingsOption(
    image: ImageVector,
    name: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = image,
            contentDescription = name,
            tint = Color.Gray
        )

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = name,
            modifier = Modifier.weight(1f)
        )

        content()
    }
}

@Composable
fun LoadWebUrl() {

    val url = "https://www.wikihow.com/Play-Munchkin"

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()

                settings.javaScriptEnabled = true;
                loadUrl(url)
            }
        },
        update = {
            it.loadUrl(url)
        }
    )
}