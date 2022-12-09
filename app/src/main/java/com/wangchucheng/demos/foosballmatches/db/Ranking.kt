package com.wangchucheng.demos.foosballmatches.db

import androidx.room.DatabaseView

/**
 * [Ranking] is the entity to store information about the ranking of a specific user. It is based on
 * a view in room database.
 *
 * @property player
 * @property numberOfGames
 * @property numberOfWins
 */
@DatabaseView(
    viewName = "rankings",
    value = "SELECT player, COUNT(score) AS numberOfGames, " +
            "COUNT(CASE WHEN is_winner THEN 1 END) AS numberOfWins " +
            "FROM scores GROUP BY player"
)
data class Ranking(
    val player: String,
    val numberOfGames: Int,
    val numberOfWins: Int
)