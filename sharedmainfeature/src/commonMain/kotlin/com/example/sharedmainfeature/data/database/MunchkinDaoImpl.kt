package com.example.sharedmainfeature.data.database

import com.example.sharedmainfeature.objects.Munchkin
import com.example.sharedmainfeature.objects.RGBColor
import com.example.sharedmainfeature.shared.cache.MunchkinDatabase

class MunchkinDaoImpl(munchkinDatabase: MunchkinDatabase) : MunchkinDao {

    private val munchkinQueries = munchkinDatabase.munchkinDatabaseQueries

    override fun insertMunchkin(munchkin: Munchkin) {
        munchkinQueries.insertMunchkin(
            id = munchkin.id?.toLong(),
            name = munchkin.name,
            level = munchkin.level.toLong(),
            strength = munchkin.strength.toLong(),
            sex = munchkin.sex
        )

        munchkinQueries.insertColor(
            munchkin.name.hashCode().toLong(),
            red = munchkin.colorRGB.red,
            green = munchkin.colorRGB.green,
            blue = munchkin.colorRGB.blue,
        )
    }

    override fun getAllMunchkins(): List<Munchkin> {
        return munchkinQueries.selectAllMunchkins(::mapMunchkins).executeAsList()
    }

    override fun updateMunchkinById(munchkin: Munchkin) {
        munchkinQueries.insertMunchkin(
            id = munchkin.id?.toLong(),
            name = munchkin.name,
            level = munchkin.level.toLong(),
            strength = munchkin.strength.toLong(),
            sex = munchkin.sex
        )

        munchkinQueries.insertColor(
            munchkin.name.hashCode().toLong(),
            red = munchkin.colorRGB.red,
            green = munchkin.colorRGB.green,
            blue = munchkin.colorRGB.blue,
        )
    }

    override fun deleteMunchkinById(munchkinId: Int) {
        munchkinQueries.selectMunchkinById(
            munchkinId.toLong(),
            ::mapMunchkins
        ).executeAsOneOrNull()?.let {
            munchkinQueries.deleteColorById(it.name.hashCode().toLong())
        }
        munchkinQueries.deleteMunchkinById(munchkinId.toLong())
    }

    override fun selectMunchkinByName(munchkinName: String): Munchkin? {
        return munchkinQueries.selectMunchkinByName(
            munchkinName,
            ::mapMunchkins
        ).executeAsOneOrNull()
    }

    private fun mapMunchkins(
        id: Long,
        name: String,
        level: Long,
        strength: Long,
        sex: String
    ): Munchkin {

        val color = munchkinQueries.selectColorById(
            name.hashCode().toLong(),
            ::mapColor
        ).executeAsOne()

        return Munchkin(
            id = id.toInt(),
            name = name,
            level = level.toInt(),
            strength = strength.toInt(),
            sex = sex,
            colorRGB = color
        )
    }

    private fun mapColor(
        id: Long,
        red: Double,
        green: Double,
        blue: Double
    ) = RGBColor(
        red = red,
        green = green,
        blue = blue
    )
}