package uz.gita.game2048v1.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import uz.gita.game2048v1.data.model.SideEnum
import kotlin.math.abs

class MyTouchListener(context: Context) : View.OnTouchListener {
    private val gestureDetector = GestureDetector(context, MyGestureDetector())
    private var actionSideEnumListener: ((SideEnum) -> Unit)? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }

    private inner class MyGestureDetector : SimpleOnGestureListener() {
        override fun onFling(
            startEvent: MotionEvent?,
            endEvent: MotionEvent,
            velocityX: Float,
            velocityY: Float,
        ): Boolean {
            if (abs(startEvent!!.x - endEvent.x) <= 100 && abs(startEvent.y - endEvent.y) <= 100)
                return true

            if (abs(startEvent.x - endEvent.x) < abs(startEvent.y - endEvent.y)) {
                if (startEvent.y < endEvent.y) actionSideEnumListener?.invoke(SideEnum.DOWN)
                else actionSideEnumListener?.invoke(SideEnum.UP)
            } else {
                if (startEvent.x > endEvent.x) actionSideEnumListener?.invoke(SideEnum.LEFT)
                else actionSideEnumListener?.invoke(SideEnum.RIGHT)
            }

            return super.onFling(startEvent, endEvent, velocityX, velocityY)
        }
    }

    fun setActionSideEnumListener(block: (SideEnum) -> Unit) {
        this.actionSideEnumListener = block
    }
}

