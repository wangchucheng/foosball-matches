package com.wangchucheng.demos.foosballmatches.ui.match

import androidx.lifecycle.ViewModel
import com.wangchucheng.demos.foosballmatches.db.FoosballRepository

/**
 * [MatchViewModel] is the view model for match overview page. It holds all the available matches.
 *
 * @param foosballRepository
 */
class MatchViewModel(foosballRepository: FoosballRepository) : ViewModel() {
    val matches = foosballRepository.getAllMatchesWithScores()
}