package com.example.sharedmainfeature.data

import com.example.munchkinlevelcounter.providers.AbstractFlowProvider
import com.example.sharedmainfeature.data.database.MunchkinDao
import com.example.sharedmainfeature.objects.Munchkin

class Provider(
    private val munchkinDao: MunchkinDao
) : AbstractFlowProvider() {

    fun insertMunchkin(munchkin: Munchkin) = resultFlowSafe {
        if (munchkinDao.selectMunchkinByName(munchkin.name) != null) {
            throw MunchkinAlreadyExistError()
        }
        munchkinDao.insertMunchkin(munchkin)
        munchkinDao.getAllMunchkins()
    }

    fun getAllMunchkins() = resultFlowSafe {
        munchkinDao.getAllMunchkins()
    }

    fun updateMunchkinById(munchkin: Munchkin) = resultFlowSafe {
        val tmp = munchkinDao.selectMunchkinByName(munchkin.name)
        if (tmp?.name == munchkin.name && tmp.id != munchkin.id) {
            throw MunchkinAlreadyExistError()
        }
        munchkinDao.updateMunchkinById(munchkin)
        munchkinDao.getAllMunchkins()
    }

    fun deleteMunchkinById(munchkinId: Int) = resultFlowSafe {
        munchkinDao.deleteMunchkinById(munchkinId)
        munchkinDao.getAllMunchkins()
    }

    class MunchkinAlreadyExistError : Throwable(message = "Munchkin with this name already exists")
}
