package com.wangchucheng.demos.foosballmatches.db

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

/**
 * [MatchWithScores] is the entity to store the whole match object with common match information and
 * scores associated to it. Match-score is an one-to-many relationship here.
 *
 * By extracting score information, database normalization is ensured and there is no data
 * redundancy.
 *
 * @property match
 * @property scores
 */
data class MatchWithScores(
    @Embedded var match: Match,
    @Relation(
        parentColumn = "id",
        entityColumn = "match_id"
    )
    val scores: List<Score>
) : Serializable