package com.wangchucheng.demos.foosballmatches.db

import androidx.room.*
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

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
    fun insert(matchWithScores: MatchWithScores) {
        // Insert scores after match insertion finished
        insertMatch(match = matchWithScores.match).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io()).subscribe(
                MatchSingleObserver(onSuccessCallback = { matchId ->
                    matchWithScores.scores.forEach {
                        it.matchId = matchId
                        insertScore(score = it).subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .subscribe(
                                MatchSingleObserver(onSuccessCallback = {})
                            )
                    }
                })
            )
    }

    @Insert
    protected abstract fun insertMatch(match: Match): Single<Long>

    @Insert
    protected abstract fun insertScore(score: Score): Single<Long>

    class MatchSingleObserver(private val onSuccessCallback: (Long) -> Unit) :
        SingleObserver<Long> {
        override fun onSubscribe(d: Disposable) {
        }

        override fun onSuccess(t: Long) {
            onSuccessCallback(t)
        }

        // throw an exception can make @Transaction rollback
        override fun onError(e: Throwable) {
            throw Exception("Failed to insert match")
        }
    }

    /**
     * Update MatchWithScore object which contains one match and multiple scores.
     *
     * This operation needs to be atomic to keep data integrity.
     *
     * @param matchWithScores
     */
    @Transaction
    @Update
    fun update(matchWithScores: MatchWithScores) {
        // Update scores after match update finished
        updateMatch(match = matchWithScores.match).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io()).subscribe(
                MatchCompletableObserver(onCompleteListener = {
                    matchWithScores.scores.forEach {
                        updateScore(score = it).subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .subscribe(MatchCompletableObserver(onCompleteListener = {}))
                    }
                })
            )
    }

    @Update
    protected abstract fun updateMatch(match: Match): Completable

    @Update
    protected abstract fun updateScore(score: Score): Completable

    class MatchCompletableObserver(private val onCompleteListener: () -> Unit) :
        CompletableObserver {
        override fun onSubscribe(d: Disposable) {}

        override fun onComplete() {
            onCompleteListener()
        }

        // throw an exception can make @Transaction rollback
        override fun onError(e: Throwable) {
            throw Exception("Failed to operate match")
        }
    }

    /**
     * Delete MatchWithScore object which contains one match and multiple scores.
     *
     * This operation needs to be atomic to keep data integrity.
     *
     * @param matchWithScores
     */
    @Transaction
    @Delete
    fun delete(matchWithScores: MatchWithScores) {
        val task: MutableList<Completable> = mutableListOf()
        matchWithScores.scores.forEach {
            task.add(deleteScore(score = it))
        }
        // Need to merge all delete operation and wait for all of them to finish
        Completable.merge(task).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
            .subscribe(MatchCompletableObserver(onCompleteListener = {
                deleteMatch(match = matchWithScores.match).subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io()).subscribe(MatchCompletableObserver(
                        onCompleteListener = {}
                    ))
            }))
    }

    @Delete
    protected abstract fun deleteMatch(match: Match): Completable

    @Delete
    protected abstract fun deleteScore(score: Score): Completable

    /**
     * Get all matches in a livedata object.
     *
     * ViewModels can be notified if the data changed.
     *
     * @return all matches with scores in LiveData
     */
    @Transaction
    @Query("SELECT * FROM matches ORDER BY id DESC")
    abstract fun getAllMatchesWithScores(): Flowable<List<MatchWithScores>>

    /**
     * Get all rankings in a livedata object.
     *
     * ViewModels can be notified if the data changed.
     *
     * @return all rankings in LiveData
     */
    @Query("SELECT * FROM rankings ORDER BY numberOfWins DESC")
    abstract fun getAllRankings(): Flowable<List<Ranking>>
}