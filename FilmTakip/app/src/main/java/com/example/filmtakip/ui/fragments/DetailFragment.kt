package com.example.filmtakip.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.filmtakip.R
import com.example.filmtakip.data.entity.FavoriteMovie
import com.example.filmtakip.data.entity.MovieDetailResponse
import com.example.filmtakip.databinding.FragmentDetailBinding
import com.example.filmtakip.ui.viewmodel.DetailViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.let { DetailFragmentArgs.fromBundle(it).movieId }

        if (movieId != null) {
            fetchMovieDetails(movieId)
        }

        setupObservers()
        setupFavoriteIcon()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchMovieDetails(movieId: Long) {
        detailViewModel.fetchMovieDetails(movieId)
    }

    private fun setupObservers() {
        detailViewModel.movieDetails.observe(viewLifecycleOwner) { movieDetails ->
            movieDetails?.let { populateMovieDetails(it) }
        }

        detailViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        detailViewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->

        }
    }

    private fun populateMovieDetails(movieDetails: MovieDetailResponse) {
        binding.apply {
            movieTitle.text = movieDetails.title
            movieReleaseDate.text = "Release Date: ${movieDetails.release_date}"
            movieRating.text = "Rating: ${movieDetails.vote_average}"
            movieRuntime.text = "Runtime: ${movieDetails.runtime} minutes"
            movieOverview.text = movieDetails.overview
            genreChipGroup.removeAllViews()

            movieDetails.genres.forEach { genre ->
                val chip = Chip(context).apply {
                    text = genre.name
                    isClickable = false
                    isCheckable = false
                }
                genreChipGroup.addView(chip)
            }

            val posterUrl = "https://image.tmdb.org/t/p/w500${movieDetails.poster_path}"
            Glide.with(this@DetailFragment)
                .load(posterUrl)
                .into(moviePoster)
        }
    }

    private fun setupFavoriteIcon() {
        binding.imageViewFav.setOnClickListener {
            val movieDetails = detailViewModel.movieDetails.value
            movieDetails?.let {
                val favoriteMovie = FavoriteMovie(
                    id = it.id.toInt(),
                    title = it.title,
                    posterPath = it.poster_path,
                    releaseDate = it.release_date,
                    voteAverage = it.vote_average
                )

                if (detailViewModel.isFavorite.value == true) {
                    detailViewModel.removeFavorite(favoriteMovie)
                    Toast.makeText(requireContext(), "${it.title} removed from favorites", Toast.LENGTH_SHORT).show()
                } else {
                    detailViewModel.addFavorite(favoriteMovie)
                    Toast.makeText(requireContext(), "${it.title} added to favorites", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

