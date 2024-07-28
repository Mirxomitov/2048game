package uz.gita.game2048v1.screen.play

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.game2048v1.domain.AppRepository
import uz.gita.game2048v1.domain.AppRepositoryImpl

class PlayModel : ViewModel() {
    private val repository: AppRepository = AppRepositoryImpl.getInstance()

    fun getMatrix() = repository.getMatrix()
    fun moveLeft() = repository.moveLeft()
    fun moveRight() = repository.moveRight()
    fun moveDown() = repository.moveDown()
    fun moveUp() = repository.moveUp()

    fun startNewGame() {
        repository.startNewGame()
    }

    fun getCurrentRecord(): MutableLiveData<Long> {
        return repository.getCurrentRecord()
    }

    fun getMaxRecord(): MutableLiveData<Long> {
        return repository.getMaxRecord()
    }

    fun backOneStep() {
        repository.backOneStep()
    }

    fun saveResult() {
        repository.saveResult()
    }

    fun saveIsWin() {
        repository.saveIsWin()
    }

    fun getIsWin(): Boolean = repository.getIsWin()
    fun saveButtonsState() {
        repository.saveButtonsState()
    }

    fun saveIsWinHelper(num: Int) {
        repository.saveIsWinHelper(num)
    }

    fun getNum() = repository.getNum()
    fun canMoveBack() = repository.canMoveBack()
}