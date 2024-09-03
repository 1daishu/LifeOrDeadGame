package com.dev.lifeordead.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev.lifeordead.R
import com.dev.lifeordead.presentation.data.Ceil
import com.dev.lifeordead.databinding.ElementCeilBinding

class HomeAdapter : ListAdapter<Ceil, HomeAdapter.HomeViewHolder>(CeilDiffCallback()) {

    inner class HomeViewHolder(
        private val binding: ElementCeilBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ceil: Ceil) {
            binding.apply {
                if (ceil.imageResId != null) {
                    imgCeil.setImageResource(ceil.imageResId)
                } else {
                    imgCeil.setImageResource(
                        if (ceil.isAlive) R.drawable.round_live
                        else R.drawable.round_dead
                    )
                }
                tvName.text = ceil.name
                tvDescription.text = ceil.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ElementCeilBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CeilDiffCallback : DiffUtil.ItemCallback<Ceil>() {
        override fun areItemsTheSame(oldItem: Ceil, newItem: Ceil): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Ceil, newItem: Ceil): Boolean {
            return oldItem == newItem
        }
    }
}