package ru.alexleru.ya.gamesnumbers.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.alexleru.ya.gamesnumbers.databinding.FragmentChooseLevelBinding
import ru.alexleru.ya.gamesnumbers.domain.entity.Level

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            testLevelButton.setOnClickListener { nextGameFragment(Level.TEST) }
            easyLevelButton.setOnClickListener { nextGameFragment(Level.EASY) }
            normalLevelButton.setOnClickListener { nextGameFragment(Level.NORMAL) }
            highLevelButton.setOnClickListener { nextGameFragment(Level.HARD) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun nextGameFragment(level: Level) {

        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        )
    }

    companion object {

        const val NAME_FRAGMENT = "ChooseLevelFragment"
        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }
}