package com.example.filmtakip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmtakip.data.entity.Movie
import com.example.filmtakip.databinding.HomeRowBinding
import com.example.filmtakip.ui.fragments.HomeFragmentDirections

class HomeMovieAdapter(
    private val movieList: List<Movie>,
) : RecyclerView.Adapter<HomeMovieAdapter.HomeMovieViewHolder>() {

    inner class HomeMovieViewHolder(val binding: HomeRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMovieViewHolder {
        val binding = HomeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeMovieViewHolder, position: Int) {
        val movie = movieList[position]

        val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.binding.imageViewMoviePoster)


        holder.binding.imageViewMoviePoster.setOnClickListener {
            val gecis=HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie.id)
            Navigation.findNavController(holder.itemView).navigate(gecis)
        }
    }

    override fun getItemCount(): Int = movieList.size
}
