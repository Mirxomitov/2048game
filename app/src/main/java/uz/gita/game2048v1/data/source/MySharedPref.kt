package uz.gita.game2048v1.data.source

import android.content.Context
import android.content.SharedPreferences

class MySharedPref {
    companion object {
        private var instance: MySharedPref? = null
        private lateinit var sharedPref: SharedPreferences

        fun init(context: Context) {
            if (instance == null) {
                instance = MySharedPref()
                sharedPref = context.getSharedPreferences("SHAREDPREF", Context.MODE_PRIVATE)
            }
        }

        fun getInstance() = instance!!


        const val SHARED_PREF = "SHAREDPREF"
        const val SAVE = "SAVE"
        const val CURRENT = "CURRENT"
        const val MAX = "MAX"
        const val IS_WIN = "IS_WIN"
        const val NUM = "NUM"
    }

    fun saveLastGame(string: String): Unit = sharedPref.edit().putString(SAVE, string).apply()
    fun getSavedGame(): List<Int> {
        val str = sharedPref.getString(SAVE, "")
        val arr = str!!.split("#")

        if (arr.size != 16) return emptyList()

        val ls = ArrayList<Int>(16)
        for (i in arr.indices) {
            ls.add(arr[i].toInt())
        }

        return ls
    }

    fun saveLastUserScore(currentScore: Long): Unit = sharedPref.edit().putLong(CURRENT, currentScore).apply()
    fun getLastUserScore(): Long = sharedPref.getLong(CURRENT, 0)

    fun saveMax(max: Long): Unit = sharedPref.edit().putLong(MAX, max).apply()
    fun getMax(): Long = sharedPref.getLong(MAX, 0)

    fun setIsWin(isWin: Boolean): Unit = sharedPref.edit().putBoolean(IS_WIN, isWin).apply()
    fun getIsWin(): Boolean = sharedPref.getBoolean(IS_WIN, false)

    fun saveIsWinHelper(num: Int): Unit = sharedPref.edit().putInt(NUM, num).apply()
    fun getNum(): Int = sharedPref.getInt(NUM, 0)
}