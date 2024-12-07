package com.example.filmtakip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmtakip.data.entity.FavoriteMovie
import com.example.filmtakip.databinding.ItemFavoritesBinding

class FavoriteMoviesAdapter(var favoriteMovie: List<FavoriteMovie>,private val onDeleteClick: (FavoriteMovie) -> Unit) : RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(val binding:ItemFavoritesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding= ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val movies=favoriteMovie[position]
        holder.binding.textViewMovieTitle.text=movies.title
        holder.binding.textViewReleaseDate.text= " Release Date: ${movies.releaseDate}"
        holder.binding.textViewVoteAverage.text=" Vote Average: ${movies.voteAverage.toString()}"

        val posterUrl = "https://image.tmdb.org/t/p/w500${movies.posterPath}"
        Glide.with(holder.binding.root.context)
            .load(posterUrl)
            .into(holder.binding.imageViewMoviePoster)

        holder.binding.imageViewDelete.setOnClickListener {
            val movieToRemove = favoriteMovie[holder.adapterPosition]
            onDeleteClick(movieToRemove)
        }
    }

    override fun getItemCount(): Int {
        return favoriteMovie.size
    }
}