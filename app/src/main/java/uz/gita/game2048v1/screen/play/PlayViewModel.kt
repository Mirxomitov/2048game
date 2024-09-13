package uz.gita.game2048v1.screen.play

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayViewModel : ViewModel() {
    private val model = PlayModel()
    fun getMatrix() = model.getMatrix()
    val currentRecord = model.getCurrentRecord()
    val maxRecord = model.getMaxRecord()
    val showGameOverDialog = MutableLiveData<String>()
    val showWinDialog = MutableLiveData<String>()
    val isFinished = model.isFinished

    init {
        model.isFinished.observeForever {
            if (it) showGameOverDialog.value = "Game over"
        }
    }

    fun moveLeft() {
        model.moveLeft()
        checkAndShowWin()
    }

    fun moveRight() {
        model.moveRight()
        checkAndShowWin()

    }

    fun moveDown() {
        model.moveDown()
        checkAndShowWin()

    }

    fun moveUp() {
        model.moveUp()
        checkAndShowWin()

    }

    fun startNewGame() {
        model.startNewGame()
        model.saveIsWinHelper(0)
    }

    fun backOneStep() {
        model.backOneStep()
    }

    fun saveIsWin() {
        model.saveIsWin()
    }

    private fun checkAndShowWin() {
        if (model.getIsWin() && model.getNum() == 0) {
            showWinDialog.value = "Win"
            model.saveIsWinHelper(1)
        }
    }

    fun saveButtonsState() {
        model.saveButtonsState()
    }

    fun canMoveBack() = model.canMoveBack()
}