package ru.alexleru.ya.gamesnumbers.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.alexleru.ya.gamesnumbers.R
import ru.alexleru.ya.gamesnumbers.databinding.FragmentGameFinishedBinding
import ru.alexleru.ya.gamesnumbers.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListener()
        bindView()
    }

    private fun clickListener() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }
            })

        binding.buttonRetry.setOnClickListener { retryGame() }
    }

    private fun bindView() {
        with(binding) {
            emojiResult.setImageResource(getImgResource())

            tvRequiredAnswers.text = String.format(
                getString(R.string.required_score), gameResult.countOfRightAnswer.toString()
            )

            tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                gameResult.gameSettings.minCountOfRightAnswer.toString()
            )


            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                gameResult.gameSettings.minPercentOfRightAnswer.toString()
            )

            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage), gameResult.scorePercentage.toString()
            )

            Log.d("++++", "${gameResult.countOfRightAnswer} // ${gameResult.countOfQuestions}")
        }
    }

    private fun FragmentGameFinishedBinding.getImgResource(): Int {
        val isWinner = gameResult.winner
        return if (isWinner) R.drawable.ic_launcher_background
        else R.color.purple_200
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(RESULT)?.let {
            gameResult = it
        }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    companion object {
        private const val RESULT = "RESULT"
        fun newInstance(gameResult: GameResult) = GameFinishedFragment().apply {
            arguments = Bundle().apply {
                putParcelable(RESULT, gameResult)
            }
        }
    }
}