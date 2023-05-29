package ru.alexleru.ya.gamesnumbers.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.alexleru.ya.gamesnumbers.databinding.FragmentGameBinding
import ru.alexleru.ya.gamesnumbers.domain.entity.GameResult


class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    private val gameViewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }

    private val gameViewModel by lazy {
        ViewModelProvider(
            this,
            gameViewModelFactory
        )[GameViewModel::class.java]
    }


    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = gameViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
//        setClickListenersToOptions()
    }
//    private fun setClickListenersToOptions() {
//        for (tvOption in list) {
//            tvOption.setOnClickListener {
//                gameViewModel.chooseAnswer(tvOption.text.toString().toInt())
//            }
//        }
//    }

    private fun observeViewModel() {

//        gameViewModel.question.observe(viewLifecycleOwner) {
////            binding.tvSum.text = it.sum.toString()
////            binding.tvLeftNumber.text = it.visibleNumber.toString()
//            it.options.forEachIndexed { index, i ->
//                list[index].text = i.toString()
//            }
//        }

//        gameViewModel.formattedTime.observe(viewLifecycleOwner) {
//            binding.textTime.text = it
//        }

//        gameViewModel.progressAnswers.observe(viewLifecycleOwner) {
//            binding.tvAnswersProgress.text = it
//        }

//        gameViewModel.enoughCount.observe(viewLifecycleOwner) {
//            binding.tvAnswersProgress.setTextColor(getColorByState(it))
//        }

        gameViewModel.gameResult.observe(viewLifecycleOwner) {
            nextGameFinish(it)
        }

//        gameViewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
//            binding.progressBar.setProgress(it, true)
//        }

//        gameViewModel.enoughPercent.observe(viewLifecycleOwner) {
//            val color = getColorByState(it)
//            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
//        }

//        gameViewModel.minPercent.observe(viewLifecycleOwner) {
//            binding.progressBar.secondaryProgress = it
//        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun nextGameFinish(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(
                gameResult
            )
        )

    }
}
