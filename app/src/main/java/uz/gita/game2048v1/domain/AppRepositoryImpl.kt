package uz.gita.game2048v1.domain

import androidx.lifecycle.MutableLiveData
import uz.gita.game2048v1.data.source.MySharedPref

class AppRepositoryImpl() : AppRepository {
    companion object {
        private var instance: AppRepository? = null

        fun init() {
            if (instance == null) instance = AppRepositoryImpl()
        }

        fun getInstance() = instance!!
        const val NEW_ELEMENT = 2
    }

    private var matrix = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
    )


    private val sharedPref = MySharedPref.getInstance()
    private var isWin = sharedPref.getIsWin()
    private var sum: Long = sharedPref.getLastUserScore()
    private var maxRecord = MutableLiveData(sharedPref.getMax())
    private var currentRecord = MutableLiveData(sharedPref.getLastUserScore())
    private var canMoveBack = false
    private var prevScore = currentRecord.value!!
    private var prevMatrix: Array<Array<Int>> = Array(matrix.size) { i ->
        Array(matrix[i].size) { j ->
            matrix[i][j]
        }
    }
    private val prevSaverMatrix: Array<Array<Int>> = Array(matrix.size) { i ->
        Array(matrix[i].size) { j ->
            matrix[i][j]
        }
    }
    private var isLose = false

    init {
        if (sharedPref.getSavedGame().isEmpty()) {
            addElement()
            addElement()
        } else {
            val ls = sharedPref.getSavedGame()

            for (i in matrix.indices) {
                for (j in matrix[i].indices) {
                    matrix[i][j] = ls[i * 4 + j]
                }
            }
        }
    }

    override fun getIsWin(): Boolean {
        return isWin
    }
    override var isFinished: MutableLiveData<Boolean> = MutableLiveData()

    override fun getCurrentRecord() = currentRecord
    override fun getMaxRecord() = maxRecord

    private fun addElement() {
        val index = indexOfZero()
        if (index == -1) return

        matrix[index / 4][index % 4] = NEW_ELEMENT
    }

    // may be -1
    private fun indexOfZero(): Int {
        val zeros = arrayListOf<Int>()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) zeros.add(i * 4 + j)
            }
        }

        if (zeros.isEmpty()) return -1

        zeros.shuffle()
        return zeros[0]
    }

    override fun getMatrix() = matrix

    override fun startNewGame() {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                matrix[i][j] = 0
            }
        }

        addElement()
        addElement()
        currentRecord.value = 0L
        sharedPref.saveLastUserScore(0)
        sum = 0
        isWin = false
        isLose = false
        canMoveBack = false
    }

    override fun backOneStep() {
        if (canMoveBack) {
            for (i in matrix.indices) {
                for (j in matrix.indices) {
                    matrix[i][j] = prevMatrix[i][j]
                }
            }
            canMoveBack = false
            currentRecord.value = prevScore
            sum = prevScore
            if (isLose) isLose = false
        }
    }

    fun isFinish(): Boolean {
        if (isLose) return false// user is not able to lose 2 times

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) return false
            }
        }
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                val currentValue = matrix[i][j]

                if (j < matrix[i].size - 1 && currentValue == matrix[i][j + 1]) {
                    return false
                }

                if (i < matrix.size - 1 && currentValue == matrix[i + 1][j]) {
                    return false
                }
            }
        }

        isLose = true
        canMoveBack = true
        return true
    }

    private fun saveMaxResult() {
        if (maxRecord.value!! <= currentRecord.value!!) {
            maxRecord.value = currentRecord.value
            sharedPref.saveMax(maxRecord.value!!)
        }
    }

    override fun moveLeft() {
        var index: Int
        var canMove = false

        prevScore = currentRecord.value!!
        for (i in matrix.indices) {
            for (j in matrix.indices) {
                prevSaverMatrix[i][j] = matrix[i][j]
            }
        }

        for (i in matrix.indices) {
            index = 0

            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0 || j == 0) {
                    continue
                }

                if (matrix[i][j] == matrix[i][index]) {
                    matrix[i][index] *= 2
                    if (!isWin && matrix[i][index] == 2048) {
                        isWin = true

                    }
                    sum += matrix[i][index]
                    canMove = true

                    index++
                    matrix[i][j] = 0
                } else if (matrix[i][index] == 0) {
                    matrix[i][index] = matrix[i][j]
                    matrix[i][j] = 0
                    canMove = true
                } else {
                    matrix[i][++index] = matrix[i][j]

                    if (index != j) {
                        matrix[i][j] = 0
                        canMove = true
                    }
                }
            }
        }
        currentRecord.value = sum
        saveMaxResult()

        if (canMove) {
            addElement()
            for (i in matrix.indices) {
                for (j in matrix.indices) {
                    prevMatrix[i][j] = prevSaverMatrix[i][j]
                }
            }
            canMoveBack = true
        }

        isFinished.postValue(isFinish())
    }

    override fun moveRight() {
        var index: Int
        var canMove = false

        prevScore = currentRecord.value!!
        for (i in matrix.indices) {
            for (j in matrix.indices) {
                prevSaverMatrix[i][j] = matrix[i][j]
            }
        }

        for (i in matrix.indices) {
            index = 3

            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[i][j] == 0 || j == 3) {
                    continue
                }

                if (matrix[i][j] == matrix[i][index]) {
                    matrix[i][index] *= 2
                    if (!isWin && matrix[i][index] == 2048) {
                        isWin = true

                    }
                    sum += matrix[i][index] // sum
                    canMove = true

                    index--
                    matrix[i][j] = 0
                } else if (matrix[i][index] == 0) {
                    matrix[i][index] = matrix[i][j]
                    canMove = true

                    matrix[i][j] = 0
                } else {
                    matrix[i][--index] = matrix[i][j]
                    if (index != j) {
                        matrix[i][j] = 0
                        canMove = true
                    }
                }
            }
        }

        currentRecord.value = sum
        saveMaxResult()

        if (canMove) {
            addElement()
            for (i in matrix.indices) {
                for (j in matrix.indices) {
                    prevMatrix[i][j] = prevSaverMatrix[i][j]
                }
            }
            canMoveBack = true
        }

        isFinished.postValue(isFinish())
    }

    override fun moveUp() {
        var index: Int
        var canMove = false

        prevScore = currentRecord.value!!
        for (i in matrix.indices) {
            for (j in matrix.indices) {
                prevSaverMatrix[i][j] = matrix[i][j]
            }
        }

        for (j in matrix.indices) {
            index = 0

            for (i in matrix[j].indices) {
                if (matrix[i][j] == 0 || i == 0) {
                    continue
                }

                if (matrix[i][j] == matrix[index][j]) {
                    matrix[index][j] *= 2

                    if (!isWin && matrix[index][j] == 2048) {
                        isWin = true

                    }
                    sum += matrix[index][j]
                    canMove = true

                    matrix[i][j] = 0
                    index++
                } else if (matrix[index][j] == 0) {
                    matrix[index][j] = matrix[i][j]
                    matrix[i][j] = 0
                    canMove = true

                } else {
                    matrix[++index][j] = matrix[i][j]
                    if (index != i) {
                        matrix[i][j] = 0
                        canMove = true
                    }
                }
            }
        }
        currentRecord.value = sum
        saveMaxResult()


        if (canMove) {
            addElement()
            for (i in matrix.indices) {
                for (j in matrix.indices) {
                    prevMatrix[i][j] = prevSaverMatrix[i][j]
                }
            }
            canMoveBack = true
        }

        isFinished.postValue(isFinish())
    }

    override fun moveDown() {
        var index: Int
        var canMove = false

        prevScore = currentRecord.value!!
        for (i in matrix.indices) {
            for (j in matrix.indices) {
                prevSaverMatrix[i][j] = matrix[i][j]
            }
        }
        for (j in matrix.indices) {
            index = 3

            for (i in matrix.size - 1 downTo 0) {
                if (matrix[i][j] == 0 || i == 3) {
                    continue
                }

                if (matrix[i][j] == matrix[index][j]) {
                    matrix[index][j] *= 2

                    if (!isWin && matrix[index][j] == 2048) {
                        isWin = true

                    }

                    sum += matrix[index][j]
                    canMove = true

                    matrix[i][j] = 0
                    index--
                } else if (matrix[index][j] == 0) {
                    matrix[index][j] = matrix[i][j]
                    matrix[i][j] = 0
                    canMove = true

                } else {
                    matrix[--index][j] = matrix[i][j]
                    if (index != i) {
                        matrix[i][j] = 0
                        canMove = true
                    }
                }
            }
        }

        currentRecord.value = sum
        saveMaxResult()

        if (canMove) {
            addElement()
            for (i in matrix.indices) {
                for (j in matrix.indices) {
                    prevMatrix[i][j] = prevSaverMatrix[i][j]
                }
            }
            canMoveBack = true
        }

        isFinished.postValue(isFinish())
    }

    override fun saveIsWin() = sharedPref.setIsWin(isWin)

    override fun saveButtonsState() {
        val result = matrix.joinToString("#") {
            it.joinToString("#")
        }

        sharedPref.apply {
            saveLastGame(result)
            saveLastUserScore(currentRecord.value ?: 0)
        }
    }

    override fun saveIsWinHelper(num: Int) {
        sharedPref.saveIsWinHelper(num)
    }

    override fun getNum(): Int {
        return sharedPref.getNum()
    }

    override fun canMoveBack(): Boolean {
        return canMoveBack
    }
}

