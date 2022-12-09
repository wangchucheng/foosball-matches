package com.wangchucheng.demos.foosballmatches

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.wangchucheng.demos.foosballmatches.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate binding
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set nav controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration =
            AppBarConfiguration(
                topLevelDestinationIds = setOf(
                    R.id.matchFragment,
                    R.id.rankingFragment
                )
            )

        //Set toolbar nad bottom navigation bar using nav controller
        binding.toolbar.setupWithNavController(
            navController = navController,
            configuration = appBarConfiguration
        )
        binding.bottomNavigation.setupWithNavController(navController = navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.matchDetailFragment) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }
}