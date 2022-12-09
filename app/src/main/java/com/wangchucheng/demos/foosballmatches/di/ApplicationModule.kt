package com.wangchucheng.demos.foosballmatches.di

import android.content.Context
import com.wangchucheng.demos.foosballmatches.db.FoosballDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideFoosballDatabase() = FoosballDatabase.getInstance(context = context)

    @Singleton
    @Provides
    fun provideFoosballDatabaseDao(database: FoosballDatabase) = database.foosballDatabaseDao
}