package uz.gita.game2048v1.screen.play

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.game2048v1.MainActivity
import uz.gita.game2048v1.R
import uz.gita.game2048v1.data.model.SideEnum
import uz.gita.game2048v1.databinding.ScreenPlayBinding
import uz.gita.game2048v1.screen.dialog.GameOverDialog
import uz.gita.game2048v1.screen.dialog.RestartDialog
import uz.gita.game2048v1.screen.dialog.WinDialog
import uz.gita.game2048v1.utils.MyBackgroundUtil
import uz.gita.game2048v1.utils.MyTouchListener
import uz.gita.game2048v1.utils.animateOnClick
import uz.gita.game2048v1.utils.popBackStack

class PlayScreen : Fragment(R.layout.screen_play) {
    private val binding by viewBinding(ScreenPlayBinding::bind)
    private val viewModel = PlayViewModel()
    private var list = ArrayList<AppCompatTextView>(16)

    private var observer = Observer<Boolean> {
        if (it) {
            val dialog = WinDialog()
            dialog.apply {
                arguments = bundleOf(
                    Pair("MAX", binding.bestScore.text.toString()),
                    Pair("CURRENT", binding.currentScore.text.toString())
                )
            }
            dialog.setListener {
                dialog.dismiss()
            }
            dialog.show(parentFragmentManager, "WinDialog")
        }
    }


    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.currentRecord.observe(this@PlayScreen) {
            binding.currentScore.text = it.toString()
        }

        viewModel.maxRecord.observe(this@PlayScreen) {
            binding.bestScore.text = it.toString()
        }

        viewModel.showGameOverDialog.observe(this@PlayScreen) {
            val dialog = GameOverDialog()
            dialog.isCancelable = false
            dialog.apply {
                arguments = bundleOf(
                    Pair("MAX", binding.bestScore.text.toString()),
                    Pair("CURRENT", binding.currentScore.text.toString())
                )
            }

            dialog.setBackListener {
                dialog.dismiss()
                viewModel.backOneStep()
                loadData()
            }

            dialog.setPlayListener {
                viewModel.startNewGame()
                dialog.dismiss()
                loadData()
            }

            dialog.setHomeListener {
                viewModel.startNewGame()
                loadData()
                dialog.dismiss()
                (requireActivity() as MainActivity).popBackStack()
            }

            dialog.show(parentFragmentManager, "GameOverDialog")
        }


        viewModel.showWinDialog.observe(this) {

            val dialog = WinDialog()
            dialog.apply {
                arguments = bundleOf(
                    Pair("MAX", binding.bestScore.text.toString()),
                    Pair("CURRENT", binding.currentScore.text.toString())
                )
            }
            dialog.setListener {
                dialog.dismiss()
            }
            dialog.show(parentFragmentManager, "WinDialog")

        }

        binding.btnHome.animateOnClick {
            viewModel.saveButtonsState()
            viewModel.saveIsWin()
            (requireContext() as MainActivity).popBackStack()
        }

        binding.btnBack.setOnSingleClickListener {
            viewModel.backOneStep()
            loadData()
        }

        binding.btnRestart.animateOnClick {
            val dialog = RestartDialog()
            dialog.setBtnYesListener {
                viewModel.startNewGame()
                loadData()
            }
            dialog.show(parentFragmentManager, "Restart dialog")
        }

        initView()
        attachTouchListener()
        loadData()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun attachTouchListener() {
        val myTouchListener = MyTouchListener(requireContext())
        myTouchListener.setActionSideEnumListener {
            when (it) {
                SideEnum.LEFT -> viewModel.moveLeft()
                SideEnum.RIGHT -> viewModel.moveRight()
                SideEnum.UP -> viewModel.moveUp()
                SideEnum.DOWN -> viewModel.moveDown()
            }
            loadData()
        }
        binding.root.setOnTouchListener(myTouchListener)
    }

    private fun initView() {
        for (i in 0 until binding.btnContainer.childCount) {
            val ls = binding.btnContainer.getChildAt(i) as LinearLayoutCompat

            for (j in 0 until ls.childCount) {
                list.add(ls.getChildAt(j) as AppCompatTextView)
            }
        }
    }

    private fun loadData() {
        binding.btnBack
            .animate()
            .setDuration(100L)
            .scaleX(if (viewModel.canMoveBack()) 1f else 0f)
            .scaleY(if (viewModel.canMoveBack()) 1f else 0f)

        val matrix = viewModel.getMatrix()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                list[i * 4 + j].text = if (matrix[i][j] == 0) "" else "${matrix[i][j]}"
                list[i * 4 + j].setBackgroundResource(MyBackgroundUtil.backgroundByAmount(matrix[i][j]))
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveButtonsState()
        viewModel.saveIsWin()
    }
}