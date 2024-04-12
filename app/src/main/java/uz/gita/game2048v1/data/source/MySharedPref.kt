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
    }

    fun saveLastGame(string: String): MySharedPref {
        sharedPref.edit().putString("SAVE", string).apply()
        //Log.d("TTT", "shared save -> $string")
        return instance!!
    }

    fun getSavedGame(): List<Int> {
        val str = sharedPref.getString("SAVE", "")
        val arr = str!!.split("#")

//        Log.d("TTT", "array size -> ${arr.size}")
//        Log.d("TTT", "array -> $arr")
        if (arr.size != 16) return emptyList()

        val ls = ArrayList<Int>(16)
        for (i in arr.indices) {
            ls.add(arr[i].toInt())
        }

        return ls
    }

    fun saveLastUserScore(currentScore: Long) {
        sharedPref.edit().putLong("CURRENT", currentScore).apply()
    }

    fun getLastUserScore(): Long = sharedPref.getLong("CURRENT", 0)

    fun getMax(): Long {
        return sharedPref.getLong("MAX", 0)
    }

    fun saveMax(max: Long) {
        sharedPref.edit().putLong("MAX", max).apply()
    }

    fun getIsWin(): Boolean = sharedPref.getBoolean("ISWIN", false)

    fun setIsWin(isWin : Boolean) {
        sharedPref.edit().putBoolean("ISWIN", isWin).apply()
    }

    fun saveIsWinHelper(num: Int) {
        sharedPref.edit().putInt("NUM", num).apply()
    }

    fun getNum(): Int {
        return sharedPref.getInt("NUM", 0)
    }
}