package uz.gita.game2048v1.utils

import android.view.View
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.popBackStack() {
    supportFragmentManager.popBackStack()
}

fun View.animateOnClick(animate: Boolean = true, body: () -> Unit) {
    if (animate) this.setOnSingleClickListener {
        this.animate()
            .setDuration(200)
            .scaleX(0.8f)
            .scaleY(0.8f)
            .withEndAction {
                this.animate()
                    .setDuration(250)
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .withEndAction {
                        body()
                    }
            }
    } else this.setOnSingleClickListener { body() }
}

class OnSingleClickListener : View.OnClickListener {

    private val onClickListener: View.OnClickListener

    constructor(listener: View.OnClickListener) {
        onClickListener = listener
    }

    constructor(listener: (View) -> Unit) {
        onClickListener = View.OnClickListener { listener.invoke(it) }
    }

    override fun onClick(v: View) {
        val currentTimeMillis = System.currentTimeMillis()

        if (currentTimeMillis >= previousClickTimeMillis + DELAY_MILLIS) {
            previousClickTimeMillis = currentTimeMillis
            onClickListener.onClick(v)
        }
    }

    companion object {
        private const val DELAY_MILLIS = 200L
        private var previousClickTimeMillis = 0L
    }
}

fun View.setOnSingleClickListener(l: View.OnClickListener) {
    setOnClickListener(OnSingleClickListener(l))
}

fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}

