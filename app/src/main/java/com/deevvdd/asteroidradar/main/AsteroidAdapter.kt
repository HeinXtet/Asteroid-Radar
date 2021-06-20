package com.deevvdd.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deevvdd.asteroidradar.databinding.LayoutAsteroidItemBinding
import com.deevvdd.asteroidradar.domain.model.Asteroid

/**
 * Created by heinhtet deevvdd@gmail.com on 20,June,2021
 */

interface AsteroidAdapterCallback {
    fun onItemClickListener(item: Asteroid)
}

class AsteroidAdapter(private val asteroidAdapterCallback: AsteroidAdapterCallback) :
    ListAdapter<Asteroid, AsteroidAdapter.AsteroidVH>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidVH {
        return AsteroidVH(
            LayoutAsteroidItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            asteroidAdapterCallback
        )
    }

    override fun onBindViewHolder(holder: AsteroidVH, position: Int) {
        holder.onBind(getItem(position))
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Asteroid>() {
            override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    inner class AsteroidVH(
        private val binding: LayoutAsteroidItemBinding,
        asteroidAdapterCallback: AsteroidAdapterCallback
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Asteroid) {
            binding.apply {
                asteroid = item
            }
            binding.lyLayoutRoot.setOnClickListener {
                asteroidAdapterCallback.onItemClickListener(
                    item
                )
            }
            binding.executePendingBindings()
        }
    }

}

