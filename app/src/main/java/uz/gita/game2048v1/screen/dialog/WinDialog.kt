package uz.gita.game2048v1.screen.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.game2048v1.R
import uz.gita.game2048v1.databinding.DialogWinBinding
import uz.gita.game2048v1.utils.animateOnClick


class WinDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view: View = inflater.inflate(R.layout.dialog_win, container, false)
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Theme_Game2048v1)
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes

        // Get the display metrics to calculate the desired width
        val displayMetrics = resources.displayMetrics
        val screenWidth = (displayMetrics.widthPixels * 0.9).toInt()

        // Set the width to 90% of the screen width
        params.width = screenWidth
        params.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun getTheme(): Int {
        return R.style.MyThemeWithBottomToCenterAnimation
    }

    private val binding by viewBinding(DialogWinBinding::bind)
    private var listener: (() -> Unit)? = null

    fun setListener(block: () -> Unit) {
        listener = block
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val current = arguments?.getString("CURRENT", "")
        val max = arguments?.getString("MAX", "")

        binding.tvCurrentScore.text = current
        binding.tvHighScore.text = max

        binding.btnPlay.animateOnClick {
            listener?.invoke()
            dialog?.dismiss()
        }
    }
}