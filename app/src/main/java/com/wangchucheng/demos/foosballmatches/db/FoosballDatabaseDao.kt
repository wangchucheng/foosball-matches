package com.wangchucheng.demos.foosballmatches.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * [FoosballDatabaseDao] is the data access object that contains basic queries of foosball database.
 *
 */
@Dao
abstract class FoosballDatabaseDao {
    /**
     * Insert MatchWithScore object which contains one match and multiple scores.
     *
     * This operation needs to be atomic to keep data integrity.
     *
     * @param matchWithScores
     */
    @Transaction
    @Insert
    suspend fun insert(matchWithScores: MatchWithScores) {
        val matchId = insertMatch(match = matchWithScores.match)

        matchWithScores.scores.forEach {
            it.matchId = matchId
            insertScore(score = it)
        }
    }

    @Insert
    protected abstract suspend fun insertMatch(match: Match): Long

    @Insert
    protected abstract suspend fun insertScore(score: Score): Long

    /**
     * Update MatchWithScore object which contains one match and multiple scores.
     *
     * This operation needs to be atomic to keep data integrity.
     *
     * @param matchWithScores
     */
    @Transaction
    @Update
    suspend fun update(matchWithScores: MatchWithScores) {
        updateMatch(match = matchWithScores.match)

        matchWithScores.scores.forEach {
            updateScore(score = it)
        }
    }

    @Update
    protected abstract suspend fun updateMatch(match: Match)

    @Update
    protected abstract suspend fun updateScore(score: Score)

    /**
     * Delete MatchWithScore object which contains one match and multiple scores.
     *
     * This operation needs to be atomic to keep data integrity.
     *
     * @param matchWithScores
     */
    @Transaction
    @Delete
    suspend fun delete(matchWithScores: MatchWithScores) {
        matchWithScores.scores.forEach {
            deleteScore(score = it)
        }

        deleteMatch(match = matchWithScores.match)
    }

    @Delete
    protected abstract suspend fun deleteMatch(match: Match)

    @Delete
    protected abstract suspend fun deleteScore(score: Score)

    /**
     * Get all matches in a livedata object.
     *
     * ViewModels can be notified if the data changed.
     *
     * @return all matches with scores in LiveData
     */
    @Transaction
    @Query("SELECT * FROM matches ORDER BY id DESC")
    abstract fun getAllMatchesWithScores(): LiveData<List<MatchWithScores>>

    /**
     * Get all rankings in a livedata object.
     *
     * ViewModels can be notified if the data changed.
     *
     * @return all rankings in LiveData
     */
    @Query("SELECT * FROM rankings ORDER BY numberOfWins DESC")
    abstract fun getAllRankings(): LiveData<List<Ranking>>
}