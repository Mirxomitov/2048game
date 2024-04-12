package uz.gita.game2048v1.data.source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.game2048v1.data.model.RecordData
import uz.gita.game2048v1.data.source.dao.RecordDao

@Database(entities = [RecordData::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    companion object {
        private var instance: MyDatabase? = null

        fun init(context: Context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, MyDatabase::class.java, "game2048.db")
                    .build()
            }
        }

        fun getInstance() = instance!!
    }

    abstract fun recordDao(): RecordDao
}