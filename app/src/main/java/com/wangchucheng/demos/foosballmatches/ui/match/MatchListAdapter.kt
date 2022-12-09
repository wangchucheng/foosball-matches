package com.wangchucheng.demos.foosballmatches.ui.match

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wangchucheng.demos.foosballmatches.databinding.ListItemMatchBinding
import com.wangchucheng.demos.foosballmatches.db.MatchWithScores

/**
 * [MatchListAdapter] is the adapter for match recycler view to populate data.
 *
 * @property clickListener
 */
class MatchListAdapter(private val clickListener: MatchItemClickListener) :
    ListAdapter<MatchWithScores, MatchListAdapter.ViewHolder>(MatchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent = parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        return holder.bind(clickListener, item)
    }

    // Refactor it using kotlin extensions for better readability
    class ViewHolder private constructor(private val binding: ListItemMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: MatchItemClickListener, item: MatchWithScores) {
            if (item.scores.size < 2) {
                throw IllegalArgumentException("Not enough scores in object")
            }
            binding.matchWithScores = item
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemMatchBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

/**
 * [MatchItemClickListener] contians the click function for match item.
 *
 * Using a class for this listener to leverage data binding usage in xml.
 *
 * @property clickListener
 */
class MatchItemClickListener(val clickListener: (matchWithScores: MatchWithScores) -> Unit) {
    fun onClick(matchWithScores: MatchWithScores) = clickListener(matchWithScores)
}

/**
 * [MatchDiffCallback] implements the DiffUtil.ItemCallback which provides a better performance and
 * appropriate animation if the data in adapter changes.
 *
 */
class MatchDiffCallback : DiffUtil.ItemCallback<MatchWithScores>() {
    override fun areItemsTheSame(oldItem: MatchWithScores, newItem: MatchWithScores): Boolean {
        return oldItem.match.id == newItem.match.id
    }

    override fun areContentsTheSame(oldItem: MatchWithScores, newItem: MatchWithScores): Boolean {
        return oldItem == newItem
    }
}