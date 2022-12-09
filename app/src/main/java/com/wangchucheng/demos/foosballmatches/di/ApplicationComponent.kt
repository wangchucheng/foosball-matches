package com.wangchucheng.demos.foosballmatches.di

import com.wangchucheng.demos.foosballmatches.ui.match.MatchFragment
import com.wangchucheng.demos.foosballmatches.ui.matchdetail.MatchDetailFragment
import com.wangchucheng.demos.foosballmatches.ui.ranking.RankingFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent {
    fun inject(fragment: MatchFragment)

    fun inject(fragment: MatchDetailFragment)

    fun inject(fragment: RankingFragment)

}