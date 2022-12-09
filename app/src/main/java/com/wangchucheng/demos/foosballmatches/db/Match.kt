package com.wangchucheng.demos.foosballmatches.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * [Match] is the entity to save common fields in a match.
 *
 * By extracting score information, database normalization is ensured and there is no data
 * redundancy.
 *
 * @property id
 * @property timestamp
 */
@Entity(tableName = "matches")
data class Match(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis(),
)