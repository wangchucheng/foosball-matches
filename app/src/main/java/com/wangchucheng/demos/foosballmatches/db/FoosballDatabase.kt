package com.wangchucheng.demos.foosballmatches.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * [FoosballDatabase] is used to provide a singleton database instance.
 *
 */
@Database(
    entities = [Match::class, Score::class],
    views = [Ranking::class],
    version = 1,
    exportSchema = false
)
abstract class FoosballDatabase : RoomDatabase() {
    abstract val foosballDatabaseDao: FoosballDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: FoosballDatabase? = null

        fun getInstance(context: Context): FoosballDatabase {
            synchronized(this) {
                var instance = INSTANCE
                // If instance is null make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FoosballDatabase::class.java,
                        "foosball_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}