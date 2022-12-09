package com.wangchucheng.demos.foosballmatches.ui.ranking

import android.content.ActivityNotFoundException
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wangchucheng.demos.foosballmatches.MyApplication
import com.wangchucheng.demos.foosballmatches.R
import com.wangchucheng.demos.foosballmatches.databinding.FragmentRankingBinding
import javax.inject.Inject

/**
 * [RankingFragment] is the fragment to show all available rankings in recycler view.
 *
 */
class RankingFragment : Fragment() {

    @Inject
    lateinit var rankingViewModelFactory: RankingViewModelFactory

    private val rankingViewModel by viewModels<RankingViewModel> { rankingViewModelFactory }

    // Inject in onAttach
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate binding
        val binding: FragmentRankingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_ranking, container, false)

        // set data binding value
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rankingViewModel = rankingViewModel

        // Init recycler view adapter and add data to it
        val adapter = RankingListAdapter()
        binding.rankingList.adapter = adapter

        rankingViewModel.mediator.observe(viewLifecycleOwner) {
            it?.let {
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