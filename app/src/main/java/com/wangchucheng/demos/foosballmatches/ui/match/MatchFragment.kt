package com.wangchucheng.demos.foosballmatches.ui.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.wangchucheng.demos.foosballmatches.R
import com.wangchucheng.demos.foosballmatches.databinding.FragmentMatchBinding
import com.wangchucheng.demos.foosballmatches.db.FoosballDatabase
import com.wangchucheng.demos.foosballmatches.db.FoosballRepository

/**
 * [MatchFragment] is the fragment to show all available matches in a recycler view.
 *
 */
class MatchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate binding object
        val binding: FragmentMatchBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_match, container, false)

        // Init repository and view model. Will use dagger for DI in another branch.
        val foosballDatabaseDao =
            FoosballDatabase.getInstance(requireActivity().application).foosballDatabaseDao
        val foosballRepository = FoosballRepository(foosballDatabaseDao = foosballDatabaseDao)

        val matchViewModelFactory = MatchViewModelFactory(foosballRepository = foosballRepository)
        val matchViewModel =
            ViewModelProvider(this, matchViewModelFactory)[MatchViewModel::class.java]

        // Init recycler view adapter and add data to it
        val adapter = MatchListAdapter(MatchItemClickListener {
            //safe args is used to pass variables from overview fragment to detail fragment
            findNavController().navigate(
                MatchFragmentDirections.actionMatchFragmentToMatchDetailFragment(
                    title = resources.getString(R.string.update_a_match_title),
                    match = it
                )
            )
        })
        binding.matchList.adapter = adapter

        matchViewModel.matches.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        // attach onClick
        binding.newFab.setOnClickListener {
            //safe args is used to pass variables from overview fragment to detail fragment
            findNavController().navigate(
                MatchFragmentDirections.actionMatchFragmentToMatchDetailFragment(
                    title = resources.getString(R.string.add_a_match_title),
                    match = null
                )
            )
        }

        return binding.root
    }
}