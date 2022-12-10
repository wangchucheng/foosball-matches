package com.wangchucheng.demos.foosballmatches.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * [FoosballRepository] is the data source for [FoosballDatabase] containing available operations.
 *
 */
class FoosballRepository @Inject constructor() {

    @Inject
    lateinit var foosballDatabaseDao: FoosballDatabaseDao

    /**
     * Insert a [MatchWithScores] object into database.
     *
     * This operation needs to be done in non-ui thread to avoid blocking ui.
     *
     * @param matchWithScores
     */
    fun insert(matchWithScores: MatchWithScores) {
        foosballDatabaseDao.insert(matchWithScores = matchWithScores)
    }

    /**
     * Update a [MatchWithScores] object into database.
     *
     * This operation needs to be done in non-ui thread to avoid blocking ui.
     *
     * @param matchWithScores
     */
    fun update(matchWithScores: MatchWithScores) {
        foosballDatabaseDao.update(matchWithScores = matchWithScores)
    }


    /**
     * Delete a [MatchWithScores] object from database.
     *
     * This operation needs to be done in non-ui thread to avoid blocking ui.
     *
     * @param matchWithScores
     */
    suspend fun delete(matchWithScores: MatchWithScores) {
        withContext(Dispatchers.IO) {
            foosballDatabaseDao.delete(matchWithScores = matchWithScores)
        }
    }

    /**
     * Get all matches in a livedata object.
     *
     * ViewModels can be notified if the data changed.
     *
     * @return all matches with scores in LiveData
     */
    fun getAllMatchesWithScores(): LiveData<List<MatchWithScores>> =
        LiveDataReactiveStreams.fromPublisher(foosballDatabaseDao.getAllMatchesWithScores())

    /**
     * Get all rankings in a livedata object.
     *
     * ViewModels can be notified if the data changed.
     *
     * @return all rankings in LiveData
     */
    fun getAllRankings(): LiveData<List<Ranking>> =
        LiveDataReactiveStreams.fromPublisher(foosballDatabaseDao.getAllRankings())
}