//package uz.gita.game2048v1.screen.score
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.view.isVisible
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import uz.gita.game2048v1.MainActivity
//import uz.gita.game2048v1.R
//import uz.gita.game2048v1.databinding.ScreenScoresBinding
//import uz.gita.game2048v1.screen.adapter.ScoresAdapter
//import uz.gita.game2048v1.utils.animateOnClick
//import uz.gita.game2048v1.utils.popBackStack
//
//class ScoresScreen : Fragment(R.layout.screen_scores), ScoresContract.View {
//    private var _binding: ScreenScoresBinding? = null
//    private val binding get() = _binding!!
//    private val viewModel = ScoresViewModel()
//    private val adapter = ScoresAdapter()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View {
//        _binding = ScreenScoresBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    @SuppressLint("SetTextI18n", "FragmentLiveDataObserve")
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.rv.layoutManager = LinearLayoutManager(requireContext())
//        binding.rv.adapter = adapter
//
//        viewModel.twentyBestScores.observe(this) { ls ->
//            adapter.addList(ls)
//            binding.emptyPlaceHolder.isVisible = ls.isEmpty()
//        }
//
//        binding.btnBack.animateOnClick {
//            (requireContext() as MainActivity).popBackStack()
//        }
//
//        viewModel.showScores()
//    }
//}