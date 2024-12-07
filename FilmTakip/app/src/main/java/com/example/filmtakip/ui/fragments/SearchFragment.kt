package com.example.filmtakip.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmtakip.R
import com.example.filmtakip.databinding.FragmentSearchBinding
import com.example.filmtakip.ui.adapter.MovieAdapter
import com.example.filmtakip.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter
    private val searchViewModel : SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding=FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter= MovieAdapter(emptyList())
        binding.recyclerViewSearch.adapter=movieAdapter
        binding.recyclerViewSearch.layoutManager=LinearLayoutManager(requireContext())

        binding.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.editTextSearch.text.toString().trim()
                if (query.isNotEmpty()) {
                    searchViewModel.searchMovies(query)
                }
                true
            } else {
                false
            }
        }

        searchViewModel.searchResults.observe(viewLifecycleOwner) { movies ->
            movieAdapter.updateMovies(movies)
        }

    }

}