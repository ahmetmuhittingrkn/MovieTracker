package com.example.filmtakip.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmtakip.data.entity.Movie
import com.example.filmtakip.databinding.SearchRowBinding
import com.example.filmtakip.ui.fragments.HomeFragmentDirections
import com.example.filmtakip.ui.fragments.SearchFragmentDirections

class MovieAdapter(var movieList:List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    inner class MovieHolder(val binding: SearchRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding= SearchRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movies=movieList[position]
        holder.binding.textViewRating.text="Vote Average : ${movies.voteAverage.toString()}"
        holder.binding.textViewMovieName.text=movies.title
        holder.binding.textViewReleaseDate.text=" Release Date : ${movies.releaseDate}"


        val imageUrl = "https://image.tmdb.org/t/p/w500" + movies.posterPath
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .override(500,750)
            .into(holder.binding.imageViewMovie)

        holder.binding.imageViewMovie.setOnClickListener {
            val gecis=SearchFragmentDirections.actionSearchFragmentToDetailFragment(movies.id)
            Navigation.findNavController(holder.itemView).navigate(gecis)
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun updateMovies(newMovies: List<Movie>) {
        this.movieList = newMovies
        notifyDataSetChanged()
    }


}