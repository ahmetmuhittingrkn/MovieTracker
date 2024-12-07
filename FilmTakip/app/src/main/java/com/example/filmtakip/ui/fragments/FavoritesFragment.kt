package com.example.filmtakip.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmtakip.databinding.FragmentFavoritesBinding
import com.example.filmtakip.ui.adapter.FavoriteMoviesAdapter
import com.example.filmtakip.ui.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val favoritesViewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoriteMoviesAdapter(emptyList()) { movie ->
            favoritesViewModel.removeFavorite(movie)
            Toast.makeText(requireContext(), "${movie.title} removed from favorites", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewFav.adapter = adapter
        binding.recyclerViewFav.layoutManager = LinearLayoutManager(requireContext())

        favoritesViewModel.favorites.observe(viewLifecycleOwner) { favoriteMovies ->
            adapter.favoriteMovie = favoriteMovies
            adapter.notifyDataSetChanged()
        }

        favoritesViewModel.loadFavorites()
    }
}
