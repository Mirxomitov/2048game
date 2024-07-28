package uz.gita.game2048v1.screen.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.game2048v1.R
import uz.gita.game2048v1.databinding.ScreenInfoBinding
import uz.gita.game2048v1.utils.animateOnClick

class InfoScreen : Fragment(R.layout.screen_info) {
    private val binding by viewBinding(ScreenInfoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnBack.animateOnClick {
            findNavController().popBackStack()
        }

        binding.telegram.animateOnClick {
            openUrl("https://t.me/Mirxomitov")
        }

        binding.instagram.animateOnClick {
            openUrl("https://www.instagram.com/mirxomitovportfolio")
        }

        binding.gmail.animateOnClick {
            openUrl("mailto:mirxtohir@gmail.com")
        }
    }
    private fun openUrl(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }
}