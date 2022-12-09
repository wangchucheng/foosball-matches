package com.wangchucheng.demos.foosballmatches.ui.ranking

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wangchucheng.demos.foosballmatches.R
import com.wangchucheng.demos.foosballmatches.databinding.FragmentRankingBinding
import com.wangchucheng.demos.foosballmatches.db.FoosballDatabase
import com.wangchucheng.demos.foosballmatches.db.FoosballRepository

/**
 * [RankingFragment] is the fragment to show all available rankings in recycler view.
 *
 */
class RankingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate binding
        val binding: FragmentRankingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_ranking, container, false)

        // Init repository and view model. Will use dagger for DI in another branch.
        val foosballDatabaseDao =
            FoosballDatabase.getInstance(requireActivity().application).foosballDatabaseDao
        val foosballRepository = FoosballRepository(foosballDatabaseDao = foosballDatabaseDao)

        val rankingViewModelFactory =
            RankingViewModelFactory(foosballRepository = foosballRepository)
        val rankingViewModel =
            ViewModelProvider(this, rankingViewModelFactory)[RankingViewModel::class.java]

        // set data binding value
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rankingViewModel = rankingViewModel

        // Init recycler view adapter and add data to it
        val adapter = RankingListAdapter()
        binding.rankingList.adapter = adapter

        rankingViewModel.mediator.observe(viewLifecycleOwner) {
            it?.let {
                println("wcctest: $it")
                adapter.submitList(it)
            }
        }

        // attach onClick
        binding.shareFab.setOnClickListener {
            onShare("Demonstrate passing value with intent")
        }

        return binding.root
    }

    /**
     * Share current rankings to others using implicit intent.
     *
     * This function is mainly used to demonstrate intent usage for passing data between activities.
     *
     * @param shareText
     */
    private fun onShare(shareText: String) {
        val shareIntent = ShareCompat.IntentBuilder(requireActivity())
            .setText(shareText)
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(), getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}