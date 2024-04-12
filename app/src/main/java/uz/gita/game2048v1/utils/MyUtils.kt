package uz.gita.game2048v1.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun String.myLog() {
    Log.d("TTT", this)
}

fun FragmentActivity.popBackStack() {
    supportFragmentManager.popBackStack()
}