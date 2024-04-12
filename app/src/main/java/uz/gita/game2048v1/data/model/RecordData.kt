package uz.gita.game2048v1.data.model

import androidx.room.Entity

@Entity(tableName = "records", primaryKeys = ["date"])
class RecordData(
    val date: Long,
    val record: Long,
)