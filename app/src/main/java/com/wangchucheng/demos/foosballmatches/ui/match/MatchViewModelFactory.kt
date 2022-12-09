package com.wangchucheng.demos.foosballmatches.ui.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wangchucheng.demos.foosballmatches.db.FoosballRepository

/**
 * [MatchViewModelFactory] is the factory class to provide [MatchViewModel] instances.
 *
 * @property foosballRepository
 */
class MatchViewModelFactory(private val foosballRepository: FoosballRepository) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
            return MatchViewModel(foosballRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}