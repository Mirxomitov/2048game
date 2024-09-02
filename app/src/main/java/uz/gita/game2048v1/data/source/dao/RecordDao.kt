//package uz.gita.game2048v1.data.source.dao
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import uz.gita.game2048v1.data.model.RecordData
//
//@Dao
//interface RecordDao {
//    @Insert
//    fun addRecord(data: RecordData)
//
//    @Query("SELECT * FROM records ORDER BY record DESC LIMIT 20")
//    fun twentyBestScores(): List<RecordData>
//}