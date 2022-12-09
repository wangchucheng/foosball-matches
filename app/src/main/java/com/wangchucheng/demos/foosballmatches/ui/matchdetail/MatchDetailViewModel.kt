package com.wangchucheng.demos.foosballmatches.ui.matchdetail

import androidx.lifecycle.*
import com.wangchucheng.demos.foosballmatches.db.*
import kotlinx.coroutines.launch

/**
 * [MatchDetailViewModel] is the view model for match detail page. It holds scores of two players
 * and other associated information and events.
 *
 * @property foosballRepository
 * @param state
 */
class MatchDetailViewModel(
    state: SavedStateHandle,
    private val foosballRepository: FoosballRepository
) : ViewModel() {
    // Original match is used to determine if it is adding or updating and whether it has data
    // changes or not.
    private var originalMatch: MatchWithScores? = null

    val firstPlayerScore = MutableLiveData<ScoreDTO>()
    val secondPlayerScore = MutableLiveData<ScoreDTO>()

    // Event to indicate if all edit text has eligible values
    private val _hasError = MutableLiveData<Boolean>()
    val hasError: LiveData<Boolean> get() = _hasError

    // Event to indicate if ui should be navigated to a new destination
    private val _shouldNavigate = MutableLiveData<Boolean>()
    val shouldNavigate: LiveData<Boolean> get() = _shouldNavigate

    // Event to indicate if a delete button should be visible in ui
    private var _hasDeleteButton: Boolean = false
    val hasDeleteButton: Boolean get() = _hasDeleteButton

    init {
        state.get<MatchWithScores>("match")?.let {
            originalMatch = it
            _hasDeleteButton = true
            firstPlayerScore.value = ScoreDTO.from(it.scores[0])
            secondPlayerScore.value = ScoreDTO.from(it.scores[1])
        } ?: run {
            firstPlayerScore.value = ScoreDTO()
            secondPlayerScore.value = ScoreDTO()
        }
    }

    /**
     * Save button clicked.
     *
     * Save or update new match information into database. The insert or update operation is async
     * in a non-ui thread.
     *
     */
    fun onSave() {
        // Check if all fields have eligible data
        if (firstPlayerScore.value?.player.isNullOrBlank()
            || firstPlayerScore.value?.score.isNullOrBlank()
            || secondPlayerScore.value?.player.isNullOrBlank()
            || secondPlayerScore.value?.score.isNullOrBlank()
        ) {
            _hasError.value = true
            return
        }

        // Save or update match information
        viewModelScope.launch {
            val matchWithScores = MatchWithScores(
                match = Match(), scores = listOf(
                    firstPlayerScore.value?.toScore()!!,
                    secondPlayerScore.value?.toScore()!!
                )
            )

            if (matchWithScores.scores[0].score > matchWithScores.scores[1].score) {
                matchWithScores.scores[0].isWinner = true
                matchWithScores.scores[1].isWinner = false
            } else if (matchWithScores.scores[0].score < matchWithScores.scores[1].score) {
                matchWithScores.scores[0].isWinner = false
                matchWithScores.scores[1].isWinner = true
            } else {
                matchWithScores.scores[0].isWinner = false
                matchWithScores.scores[1].isWinner = false
            }

            originalMatch?.let {
                matchWithScores.match.id = it.match.id
                matchWithScores.scores[0].id = it.scores[0].id
                matchWithScores.scores[1].id = it.scores[1].id
                foosballRepository.update(matchWithScores = matchWithScores)
            } ?: run {
                foosballRepository.insert(
                    matchWithScores = matchWithScores
                )
            }
            _shouldNavigate.value = true
        }
    }

    /**
     * Delete button clicked.
     *
     * Delete current match information from database. The delete operation is async in a non-ui
     * thread.
     *
     */
    fun onDelete() {
        viewModelScope.launch {
            originalMatch?.let {
                foosballRepository.delete(matchWithScores = it)
            }
            _shouldNavigate.value = true
        }
    }

    /**
     * Showing error operation is finished.
     *
     * Reset the [hasError] event so it will not be called again in orientation changes or other
     * cases.
     *
     */
    fun doneShowingError() {
        _hasError.value = false
    }

    /**
     * Navigation operation is finished.
     *
     * Reset the [shouldNavigate] event so it will not be called again in orientation changes or
     * other cases.
     *
     */
    fun doneNavigating() {
        _shouldNavigate.value = false
    }
}