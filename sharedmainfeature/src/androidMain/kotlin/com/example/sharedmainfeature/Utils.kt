package com.example.sharedmainfeature

import androidx.compose.ui.graphics.Color
import com.example.sharedmainfeature.objects.Munchkin
import com.example.sharedmainfeature.objects.RGBColor

fun Munchkin.composeColor() = Color(
    red = this.colorRGB.red.toInt(),
    green = this.colorRGB.green.toInt(),
    blue = this.colorRGB.blue.toInt(),
)

fun RGBColor.composeColor() = Color(
    red = this.red.toInt(),
    green = this.green.toInt(),
    blue = this.blue.toInt(),
)