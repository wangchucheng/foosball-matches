package com.wangchucheng.demos.foosballmatches.ui.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wangchucheng.demos.foosballmatches.db.FoosballRepository
import javax.inject.Inject

/**
 * [MatchViewModelFactory] is the factory class to provide [MatchViewModel] instances.
 *
 */
class MatchViewModelFactory @Inject constructor() :
    ViewModelProvider.Factory {

    @Inject
    lateinit var foosballRepository: FoosballRepository

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
            return MatchViewModel(foosballRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}