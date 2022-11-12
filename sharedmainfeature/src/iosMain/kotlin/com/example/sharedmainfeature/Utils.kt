package com.example.sharedmainfeature

import com.example.sharedmainfeature.objects.Munchkin
import com.example.sharedmainfeature.objects.RGBColor
import platform.UIKit.UIColor

fun Munchkin.swiftUIColor() = UIColor(
    red = this.colorRGB.red / 255,
    green = this.colorRGB.green / 255,
    blue = this.colorRGB.blue / 255,
    alpha = 1.0
)

fun RGBColor.swiftUIColor() = UIColor(
    red = this.red / 255,
    green = this.green / 255,
    blue = this.blue / 255,
    alpha = 1.0
)