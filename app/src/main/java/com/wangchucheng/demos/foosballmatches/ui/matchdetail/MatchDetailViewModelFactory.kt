package com.wangchucheng.demos.foosballmatches.ui.matchdetail

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.wangchucheng.demos.foosballmatches.db.FoosballRepository

/**
 * [MatchDetailViewModelFactory] is the factory class to provide [MatchDetailViewModel] instances.
 *
 * @property foosballRepository
 */
class MatchDetailViewModelFactory(private val foosballRepository: FoosballRepository) :
    AbstractSavedStateViewModelFactory() {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(MatchDetailViewModel::class.java)) {
            return MatchDetailViewModel(handle, foosballRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}