package com.app.netrasehat.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.netrasehat.databinding.ItemPesanGiziseimbangBinding
import com.bumptech.glide.Glide

class PesanAdapter : RecyclerView.Adapter<PesanAdapter.PesanViewHolder>()  {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PesanAdapter.PesanViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PesanAdapter.PesanViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class PesanViewHolder(private val binding: ItemPesanGiziseimbangBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieResultsItem) {
            with(binding) {
                tvTitle.text = movie.title
                tvRelaseDate.text = movie.releaseDate
                tvOverviewItem.text = movie.overview
                itemView.setOnClickListener {
                    onItemClickListener.onMovieClicked(movie)
                }
            }
        }
    }
}