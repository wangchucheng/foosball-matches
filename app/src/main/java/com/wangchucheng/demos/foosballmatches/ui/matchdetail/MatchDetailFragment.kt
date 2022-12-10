package com.wangchucheng.demos.foosballmatches.ui.matchdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.wangchucheng.demos.foosballmatches.R
import com.wangchucheng.demos.foosballmatches.databinding.FragmentMatchDetailBinding
import com.wangchucheng.demos.foosballmatches.db.FoosballDatabase
import com.wangchucheng.demos.foosballmatches.db.FoosballRepository


/**
 * [MatchDetailFragment] is the fragment to show the detail of a match.
 *
 */
class MatchDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate binding
        val binding: FragmentMatchDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_match_detail, container, false)

        // Init repository and view model. Will use dagger for DI in another branch.
        val foosballDatabaseDao =
            FoosballDatabase.getInstance(requireActivity().application).foosballDatabaseDao
        val foosballRepository = FoosballRepository(foosballDatabaseDao = foosballDatabaseDao)

        val matchDetailViewModelFactory =
            MatchDetailViewModelFactory(foosballRepository = foosballRepository)
        val matchDetailViewModel =
            ViewModelProvider(this, matchDetailViewModelFactory)[MatchDetailViewModel::class.java]

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
        matchDetailViewModel.hasBlankError.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Names and scores can not be blank.", Toast.LENGTH_LONG)
                    .show()
                matchDetailViewModel.doneShowingError()
            }
        }

        matchDetailViewModel.hasNumberError.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Scores must be valid.", Toast.LENGTH_LONG)
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