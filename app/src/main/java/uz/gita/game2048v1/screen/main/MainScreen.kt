package uz.gita.game2048v1.screen.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.game2048v1.R
import uz.gita.game2048v1.data.source.MySharedPref
import uz.gita.game2048v1.databinding.ScreenMainBinding

class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()
        binding.btnPlay.setOnClickListener {
            navController.navigate(MainScreenDirections.actionMainScreenToPlayScreen(false))
        }

        binding.btnRecords.setOnClickListener {
            navController.navigate(MainScreenDirections.actionMainScreenToScoresScreen())
        }

        binding.info.setOnClickListener {
            navController.navigate(MainScreenDirections.actionMainScreenToInfoScreen())
        }
    }
}