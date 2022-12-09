package com.wangchucheng.demos.foosballmatches.ui.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wangchucheng.demos.foosballmatches.db.FoosballRepository

/**
 * [RankingViewModelFactory] is the factory class to provide [RankingViewModel] instances.
 *
 * @property foosballRepository
 */
class RankingViewModelFactory(private val foosballRepository: FoosballRepository) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RankingViewModel::class.java)) {
            return RankingViewModel(foosballRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}