package net.penguin.moodintunes

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.penguin.moodintunes.navigation.MainNavigationItem
import javax.inject.Inject

@HiltViewModel
class MoodInTunesMainViewModel @Inject constructor(): ViewModel() {
    private val _navBarSelectedItem = MutableStateFlow<MainNavigationItem>(MainNavigationItem.Discover)
    val navBarSelectedItem: StateFlow<MainNavigationItem> = _navBarSelectedItem

    fun onNavBarItemChanged(item: MainNavigationItem) {
        _navBarSelectedItem.value = item
    }
}