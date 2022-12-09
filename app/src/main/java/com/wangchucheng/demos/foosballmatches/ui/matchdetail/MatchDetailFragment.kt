package com.wangchucheng.demos.foosballmatches.ui.matchdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wangchucheng.demos.foosballmatches.MyApplication
import com.wangchucheng.demos.foosballmatches.R
import com.wangchucheng.demos.foosballmatches.databinding.FragmentMatchDetailBinding
import javax.inject.Inject


/**
 * [MatchDetailFragment] is the fragment to show the detail of a match.
 *
 */
class MatchDetailFragment : Fragment() {

    @Inject
    lateinit var matchDetailViewModelFactory: MatchDetailViewModelFactory

    private val matchDetailViewModel: MatchDetailViewModel by viewModels {
        matchDetailViewModelFactory
    }

    // Inject in onAttach
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate binding
        val binding: FragmentMatchDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_match_detail, container, false)

        // set data binding value
        binding.lifecycleOwner = viewLifecycleOwner
        binding.matchDetailViewModel = matchDetailViewModel

        // attach click listeners
        binding.deleteButton.setOnClickListener {
            AlertDialog.Builder(requireContext()).setTitle("Delete Match")
                .setMessage("Are you sure you want to delete this match?")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    matchDetailViewModel.onDelete()
                }.setNegativeButton(android.R.string.cancel, null).show()
        }

        // observe necessary fields in view model
        matchDetailViewModel.hasError.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Names and scores can not be black.", Toast.LENGTH_LONG)
                    .show()
                matchDetailViewModel.doneShowingError()
            }
        }

        matchDetailViewModel.shouldNavigate.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(MatchDetailFragmentDirections.actionMatchDetailFragmentToMatchFragment())
                matchDetailViewModel.doneNavigating()
            }
        }

        return binding.root
    }
}