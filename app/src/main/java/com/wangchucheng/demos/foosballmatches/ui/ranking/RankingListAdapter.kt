package com.wangchucheng.demos.foosballmatches.ui.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wangchucheng.demos.foosballmatches.databinding.ListItemRankingBinding
import com.wangchucheng.demos.foosballmatches.db.Ranking

/**
 * [RankingListAdapter] is the adapter for ranking recycler view to populate data.
 *
 */
class RankingListAdapter :
    ListAdapter<Ranking, RankingListAdapter.ViewHolder>(RankingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent = parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        return holder.bind(item)
    }

    // Refactor it using kotlin extensions for better readability
    class ViewHolder private constructor(private val binding: ListItemRankingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Ranking) {
            binding.ranking = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemRankingBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

/**
 * [RankingDiffCallback] implements the DiffUtil.ItemCallback which provides a better performance
 * and appropriate animation if the data in adapter changes.
 *
 */
class RankingDiffCallback : DiffUtil.ItemCallback<Ranking>() {
    override fun areItemsTheSame(oldItem: Ranking, newItem: Ranking): Boolean {
        return oldItem.player == newItem.player
    }

    override fun areContentsTheSame(oldItem: Ranking, newItem: Ranking): Boolean {
        return oldItem == newItem
    }
}