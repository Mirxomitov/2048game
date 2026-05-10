package uz.gita.game2048v1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import uz.mirxomitov.game2048.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        window.navigationBarColor = resources.getColor(R.color.bg_color)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        // Play Market Android Sdk 35 requirements
        WindowCompat.setDecorFitsSystemWindows(window, false)
        findViewById<FragmentContainerView>(R.id.container).applySystemBarsPadding()
    }
}

// Play Market Android Sdk 35 requirements
fun View.applySystemBarsPadding() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.setPadding(
            view.paddingLeft,
            systemBars.top,
            view.paddingRight,
            systemBars.bottom
        )
        insets
    }
}
