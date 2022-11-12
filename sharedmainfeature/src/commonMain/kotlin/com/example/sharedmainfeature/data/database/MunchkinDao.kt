package com.example.sharedmainfeature.data.database

import com.example.sharedmainfeature.objects.Munchkin

interface MunchkinDao {

    fun insertMunchkin(munchkin: Munchkin)

    fun getAllMunchkins(): List<Munchkin>

    fun updateMunchkinById(munchkin: Munchkin)

    fun deleteMunchkinById(munchkinId: Int)

    fun selectMunchkinByName(munchkinName: String): Munchkin?
}