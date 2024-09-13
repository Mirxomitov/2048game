package uz.gita.game2048v1.screen.splash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.game2048v1.R
import uz.gita.game2048v1.databinding.ScreenSplashBinding
import uz.gita.game2048v1.utils.dpToPx

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val binding by viewBinding(ScreenSplashBinding::bind)
    private var isNavigated = false // Flag to prevent multiple navigations

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rightBottom.post {
            if (isAdded && !isNavigated) { // Check if added and not navigated yet
                binding.apply {
                    val firstY = first.y
                    val secondY = second.y

                    first.y = (-100).dpToPx(requireContext())
                    second.y = binding.root.height.toFloat()

                    first.animate().setDuration(1000).y(firstY)
                        .withEndAction {
                            isNavigated = true // Set flag after navigation
                            findNavController().navigate(SplashScreenDirections.actionSplashScreenToMainScreen())
                        }

                    second.animate().setDuration(1000).y(secondY)
                }
            }
        }
    }
}