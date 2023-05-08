package ru.alexleru.ya.gamesnumbers.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.alexleru.ya.gamesnumbers.R
import ru.alexleru.ya.gamesnumbers.databinding.FragmentGameBinding
import ru.alexleru.ya.gamesnumbers.domain.entity.GameResult
import ru.alexleru.ya.gamesnumbers.domain.entity.GameSettings
import ru.alexleru.ya.gamesnumbers.domain.entity.Level
import java.lang.RuntimeException

class GameFragment : Fragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSum.setOnClickListener {
            nextGameFinish(
                GameResult(
                    true, 0, 0,
                    GameSettings(0, 0, 0, 0)
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(LEVEL)?. let { level = it }
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
