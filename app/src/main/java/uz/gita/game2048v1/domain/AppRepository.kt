package uz.gita.game2048v1.domain

import androidx.lifecycle.MutableLiveData
import uz.gita.game2048v1.data.model.RecordData

interface AppRepository {
    fun getMatrix(): Array<Array<Int>>
    fun moveLeft() : Boolean
    fun moveRight() : Boolean
    fun moveUp() : Boolean
    fun moveDown() : Boolean
    fun startNewGame()
    fun insertRecord(data : RecordData)
    fun getCurrentRecord(): MutableLiveData<Long>
    fun getMaxRecord(): MutableLiveData<Long>
    fun twentyBestScores(): List<RecordData>
    fun backOneStep()
    fun saveResult()
    fun saveIsWin()
    fun getIsWin() : Boolean
    fun saveButtonsState()
    fun saveIsWinHelper(num: Int)
    fun getNum(): Int
    fun canMoveBack() : Boolean
}