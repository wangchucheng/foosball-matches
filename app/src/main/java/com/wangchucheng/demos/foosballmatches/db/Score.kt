package com.wangchucheng.demos.foosballmatches.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * [Score] is the entity to store score information in a match.
 *
 * By extracting score information, database normalization is ensured and there is no data
 * redundancy.
 *
 * @property id
 * @property matchId
 * @property player
 * @property score
 * @property isWinner
 */
@Entity(tableName = "scores")
data class Score(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "match_id")
    var matchId: Long = 0L,

    val player: String = "",

    val score: Int = -1,

    @ColumnInfo(name = "is_winner")
    var isWinner: Boolean = false
)