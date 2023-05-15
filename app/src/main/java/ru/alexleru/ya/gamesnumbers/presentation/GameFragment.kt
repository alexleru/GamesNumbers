package ru.alexleru.ya.gamesnumbers.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.alexleru.ya.gamesnumbers.R
import ru.alexleru.ya.gamesnumbers.databinding.FragmentGameBinding
import ru.alexleru.ya.gamesnumbers.domain.entity.GameResult
import ru.alexleru.ya.gamesnumbers.domain.entity.Level

class GameFragment : Fragment() {

    private val gameViewModelFactory by lazy {
        GameViewModelFactory(level, requireActivity().application)
    }

    private val gameViewModel by lazy {
        ViewModelProvider(
            this,
            gameViewModelFactory
        )[GameViewModel::class.java]
    }

    private val list by lazy {
        listOf(
            binding.tvOption1,
            binding.tvOption2,
            binding.tvOption3,
            binding.tvOption4,
            binding.tvOption5,
            binding.tvOption6
        )
    }

    private lateinit var level: Level

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun setClickListenersToOptions() {
        for (tvOption in list) {
            tvOption.setOnClickListener {
                gameViewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {

        gameViewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            it.options.forEachIndexed { index, i ->
                list[index].text = i.toString()
            }
        }

        gameViewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.textTime.text = it
        }

        gameViewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }

        gameViewModel.enoughCount.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.setTextColor(getColorByState(it))
        }

        gameViewModel.gameResult.observe(viewLifecycleOwner) {
            nextGameFinish(it)
        }

        gameViewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }

        gameViewModel.enoughPercent.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }

        gameViewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setClickListenersToOptions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(LEVEL)?.let { level = it }
    }

    private fun nextGameFinish(gameResult: GameResult) {

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val LEVEL = "LEVEL"
        const val NAME = "GameFragment"
        fun newInstance(level: Level) = GameFragment().apply {
            arguments = Bundle().apply {
                putParcelable(LEVEL, level)
            }
        }
    }
}
