package com.example.homeworkstbc

import GameBoardAdapter
import GameCell
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homeworkstbc.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private var gridSize: Int = 9

    private var currentPlayer = "X"


    private val board = mutableListOf<String>()

    private var gameOver = false


    private val gameAdapter by lazy {
         GameBoardAdapter { position ->
            handleCellClick(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gridSize = it.getInt(ARG_GRID_SIZE)
        }
        repeat(gridSize * gridSize) {
            board.add("")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        attachRecycler()

        goBackByClicking()

    }


    private fun attachRecycler ( ) {
        binding.gameRecyclerView.apply {
            layoutManager = GridLayoutManager(context, gridSize)
            adapter = gameAdapter
        }

        gameAdapter.submitList(createBoard())
    }

    private fun handleCellClick(position: Int) {
        if (gameOver)
            return
        else if (board[position].isEmpty()) {
            board[position] = currentPlayer
            gameAdapter.submitList(createBoard())
            checkForWinner()
            switchPlayer()
        }
    }

    private fun createBoard(): List<GameCell> {
        return board.mapIndexed { index, symbol -> GameCell(index, symbol) }
    }

    private fun switchPlayer ( ) {
        currentPlayer =
            if (currentPlayer == "X")
                "O"
            else
                "X"
    }

    private fun checkForWinner() {
        for (i in 0 until gridSize) {
            val isRowWin = checkLine(i * gridSize, 1)
            val isColumnWin = checkLine(i, gridSize)

            if (isRowWin || isColumnWin) {
                return
            }
        }

        val diagonalWin1 = checkLine(0, gridSize + 1)
        val diagonalWin2 = checkLine(gridSize - 1, gridSize - 1)

        if (diagonalWin1 || diagonalWin2) {
            return
        }

        val isDraw = board.none { it.isEmpty() }
        if (isDraw) {
            gameOver = true
            Toast.makeText(requireContext(), "It's a Draw!", Toast.LENGTH_SHORT).show()
            goBack()
        }
    }


    private fun goBackByClicking ( ) {
        binding.goBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


    }
    private fun goBack ( ) {
            parentFragmentManager.popBackStack()


    }

    private fun checkLine(startIndex: Int, step: Int): Boolean {
        val symbol = board[startIndex]

        if (symbol.isEmpty()) {
            return false
        }

        for (i in 1 until gridSize) {
            val nextIndex = startIndex + i * step

            if (board[nextIndex] != symbol) {
                return false
            }
        }

        gameOver = true
        Toast.makeText(requireContext(), "$symbol Wins!", Toast.LENGTH_SHORT).show()
        goBack()
        return true
    }



    companion object {
        private const val ARG_GRID_SIZE = "grid_size"

        fun newInstance(gridSize: Int) = GameFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_GRID_SIZE, gridSize)
            }
        }
    }
    override fun onDestroyView ( ) {
        super.onDestroyView()
        _binding = null
    }

}
