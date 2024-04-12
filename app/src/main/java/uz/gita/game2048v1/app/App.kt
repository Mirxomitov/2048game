package uz.gita.game2048v1.app

import android.app.Application
import uz.gita.game2048v1.data.source.MyDatabase
import uz.gita.game2048v1.data.source.MySharedPref
import uz.gita.game2048v1.domain.AppRepositoryImpl

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        MyDatabase.init(this)
        MySharedPref.init(this)
        AppRepositoryImpl.init()
    }
}