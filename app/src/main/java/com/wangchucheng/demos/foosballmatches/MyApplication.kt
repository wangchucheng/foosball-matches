package com.wangchucheng.demos.foosballmatches

import android.app.Application
import com.wangchucheng.demos.foosballmatches.di.ApplicationComponent
import com.wangchucheng.demos.foosballmatches.di.ApplicationModule
import com.wangchucheng.demos.foosballmatches.di.DaggerApplicationComponent

class MyApplication : Application() {
    val appComponent: ApplicationComponent =
        DaggerApplicationComponent.builder().applicationModule(ApplicationModule(context = this))
            .build()
}