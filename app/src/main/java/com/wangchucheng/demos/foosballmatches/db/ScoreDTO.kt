package com.wangchucheng.demos.foosballmatches.db

/**
 * [ScoreDTO] is the dto object to be used in transferring ui data, leveraging data binding and
 * keeping null-safe for entities.
 *
 * @property matchId
 * @property player
 * @property score
 */
data class ScoreDTO(
    val matchId: Long? = null,
    var player: String = "",
    var score: String = ""
) {
    companion object {
        fun from(score: Score): ScoreDTO {
            return ScoreDTO(
                matchId = score.matchId,
                player = score.player,
                score = score.score.toString()
            )
        }
    }
}

fun ScoreDTO.toScore(): Score? {
    val scoreNum = score.toIntOrNull()
    if (player.isNotEmpty() && scoreNum != null) {
        val score = Score(player = player, score = scoreNum)
        matchId?.let {
            score.matchId = matchId
        }
        return score
    }
    return null
}