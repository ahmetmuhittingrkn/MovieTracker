package com.example.filmtakip.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmtakip.databinding.FragmentHomeBinding
import com.example.filmtakip.ui.adapter.HomeMovieAdapter
import com.example.filmtakip.ui.GridSpacingItemDecoration
import com.example.filmtakip.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: HomeMovieAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        homeViewModel.fetchPopularMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        movieAdapter = HomeMovieAdapter(emptyList())
        binding.recyclerViewHome.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter

            val spacing = dpToPx(8)
            addItemDecoration(GridSpacingItemDecoration(2, spacing, true))
        }
    }

    private fun observeViewModel() {
        homeViewModel.movies.observe(viewLifecycleOwner) { movies ->
            movieAdapter = HomeMovieAdapter(movies)
            binding.recyclerViewHome.adapter = movieAdapter
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->

        }

        homeViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * requireContext().resources.displayMetrics.density).toInt()
    }
}
