package com.example.sharedmainfeature.objects

data class Munchkin(
    val id: Int?,
    val name: String,
    val level: Int,
    val strength: Int,
    val sex: String,
    val colorRGB: RGBColor
) {

    fun totalStrength() = level + strength

    fun isEmptyMunchkin() = id == null && name == ""

    companion object {

        val colorsListRGB = listOf(
            RGBColor(255.0, 0.0, 0.0),
            RGBColor(111.0, 168.0, 220.0),
            RGBColor(143.0, 206.0, 0.0),
            RGBColor(241.0, 194.0, 50.0),
            RGBColor(166.0, 77.0, 121.0),
            RGBColor(234.0, 153.0, 153.0),
            RGBColor(116.0, 71.0, 0.0),
            RGBColor(153.0, 153.0, 153.0),
            RGBColor(41.0, 134.0, 204.0),
        )

        fun getEmptyMunchkin() = Munchkin(
            id = null,
            name = "",
            level = 1,
            strength = 0,
            sex = "male",
            colorRGB = colorsListRGB.first()
        )

        fun getAvailableSexes() = listOf("male", "female")
    }

}

data class RGBColor(
    val red: Double,
    val green: Double,
    val blue: Double
)