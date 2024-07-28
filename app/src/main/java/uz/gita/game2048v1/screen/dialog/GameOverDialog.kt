package uz.gita.game2048v1.screen.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.game2048v1.R
import uz.gita.game2048v1.databinding.DialogGameOverBinding
import uz.gita.game2048v1.utils.animateOnClick

class GameOverDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view: View = inflater.inflate(R.layout.dialog_game_over, container, false)
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

        val displayMetrics = resources.displayMetrics
        val screenWidth = (displayMetrics.widthPixels * 0.9).toInt()

        params.width = screenWidth
        params.height = LayoutParams.WRAP_CONTENT

        dialog!!.window!!.attributes = params as LayoutParams
    }

    private val binding by viewBinding(DialogGameOverBinding::bind)
    private var btnPlayListener: (() -> Unit)? = null
    private var btnBackListener: (() -> Unit)? = null
    private var btnHomeListener: (() -> Unit)? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val current = arguments?.getString("CURRENT", "")
        val max = arguments?.getString("MAX", "")

        binding.tvCurrentScore.text = current
        binding.tvHighScore.text = max

        binding.btnPlay.animateOnClick {
            if (btnPlayListener != null) btnPlayListener!!()
        }

        binding.btnHome.animateOnClick {
            btnHomeListener?.invoke()
        }

        binding.btnBack.animateOnClick {
            btnBackListener?.invoke()
        }
    }

    fun setPlayListener(block: () -> Unit) {
        btnPlayListener = block
    }

    fun setHomeListener(block: () -> Unit) {
        btnHomeListener = block
    }

    fun setBackListener(block: () -> Unit) {
        btnBackListener = block
    }

    override fun getTheme(): Int {
        return R.style.MyThemeWithBottomToCenterAnimation
    }
}