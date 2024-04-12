package uz.gita.game2048v1.screen.splash

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.game2048v1.R
import uz.gita.game2048v1.databinding.ScreenSplashBinding

class SplashScreen : Fragment(R.layout.screen_splash) {
    private val binding by viewBinding(ScreenSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rightBottom.post {
            binding.apply {
                val firstY = first.y
                val secondY = second.y

                first.y = (-100).dpToPx(requireContext())
                second.y = binding.root.height.toFloat()

                first.animate().setDuration(1000).y(firstY)
                    .withEndAction {
                        findNavController().navigate(SplashScreenDirections.actionSplashScreenToMainScreen())
                    }

                second.animate().setDuration(1000).y(secondY)
            }
        }
    }
}

fun Int.dpToPx(context: Context): Float = (this * context.resources.displayMetrics.density)
