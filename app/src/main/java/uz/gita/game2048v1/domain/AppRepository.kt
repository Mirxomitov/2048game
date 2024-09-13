package uz.gita.game2048v1.domain

import androidx.lifecycle.MutableLiveData

interface AppRepository {
    fun getMatrix(): Array<Array<Int>>
    fun moveLeft()
    fun moveRight()
    fun moveUp()
    fun moveDown()
    fun startNewGame()
    fun getCurrentRecord(): MutableLiveData<Long>
    fun getMaxRecord(): MutableLiveData<Long>
    fun backOneStep()
    fun saveIsWin()
    fun getIsWin() : Boolean
    fun saveButtonsState()
    fun saveIsWinHelper(num: Int)
    fun getNum(): Int
    fun canMoveBack() : Boolean
    val isFinished: MutableLiveData<Boolean>
}