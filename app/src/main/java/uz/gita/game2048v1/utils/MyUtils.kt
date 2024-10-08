package uz.gita.game2048v1.utils

import android.content.Context
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

class OnSingleClickListener(listener: (View) -> Unit) : View.OnClickListener {

    private val onClickListener: View.OnClickListener = View.OnClickListener { listener.invoke(it) }

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

fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}

fun Int.dpToPx(context: Context): Float = (this * context.resources.displayMetrics.density)