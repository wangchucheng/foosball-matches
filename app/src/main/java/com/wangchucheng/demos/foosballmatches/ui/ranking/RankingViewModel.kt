package com.wangchucheng.demos.foosballmatches.ui.ranking

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.wangchucheng.demos.foosballmatches.db.FoosballRepository
import com.wangchucheng.demos.foosballmatches.db.Ranking

/**
 * [RankingViewModel] is the view model for ranking overview page. It holds all the available
 * rankings.
 *
 * @param foosballRepository
 */
class RankingViewModel(private val foosballRepository: FoosballRepository) : ViewModel() {

    private val rankings = foosballRepository.getAllRankings()

    // rankings is LiveData which can not be modified, so we need a mediator to implementing sorting
    var mediator = MediatorLiveData<List<Ranking>>()

    init {
        mediator.addSource(rankings) {
            mediator.value = it
        }
    }

    /**
     * Order the ranking by number of games.
     *
     */
    fun orderByGameNum() {
        mediator.value = mediator.value?.sortedByDescending { it.numberOfGames }
    }

    /**
     * Order the ranking by number of wins.
     *
     */
    fun orderByWinNum() {
        mediator.value = mediator.value?.sortedByDescending { it.numberOfWins }
    }
}