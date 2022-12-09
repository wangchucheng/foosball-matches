package com.wangchucheng.demos.foosballmatches.ui.match

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wangchucheng.demos.foosballmatches.MyApplication
import com.wangchucheng.demos.foosballmatches.R
import com.wangchucheng.demos.foosballmatches.databinding.FragmentMatchBinding
import javax.inject.Inject

/**
 * [MatchFragment] is the fragment to show all available matches in a recycler view.
 *
 */
class MatchFragment : Fragment() {

    @Inject
    lateinit var matchViewModelFactory: MatchViewModelFactory

    private val matchViewModel by viewModels<MatchViewModel> { matchViewModelFactory }

    // Inject in onAttach
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate binding object
        val binding: FragmentMatchBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_match, container, false)

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