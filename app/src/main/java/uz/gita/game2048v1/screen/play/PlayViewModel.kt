package uz.gita.game2048v1.screen.play

import androidx.lifecycle.MutableLiveData

class PlayViewModel {
    private val model = PlayModel()
    fun getMatrix() = model.getMatrix()
    val currentRecord = model.getCurrentRecord()
    val maxRecord = model.getMaxRecord()
    val showGameOverDialog = MutableLiveData<String>()
    val showWinDialog = MutableLiveData<String>()

    fun moveLeft() {
        if (!model.moveLeft()) {
            showGameOverDialog.value = "Game over"
        }
        checkAndShowWin()
    }

    fun moveRight() {
        if (!model.moveRight()) {
            showGameOverDialog.value = "Game over"
        }
        checkAndShowWin()

    }

    fun moveDown() {
        if (!model.moveDown()) {
            showGameOverDialog.value = "Game over"
        }
        checkAndShowWin()

    }

    fun moveUp() {
        if (!model.moveUp()) {
            showGameOverDialog.value = "Game over"
        }
        checkAndShowWin()

    }

    fun startNewGame() {
        model.startNewGame()
        model.saveIsWinHelper(0)
    }

    fun backOneStep() {
        model.backOneStep()
    }

    fun saveResult() {
        model.saveResult()
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
}