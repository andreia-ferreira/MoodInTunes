package net.penguin.moodintunes

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.penguin.common_design.navigation.BottomNavBarNavigation
import javax.inject.Inject

@HiltViewModel
class MoodInTunesMainViewModel @Inject constructor(): ViewModel() {
    private val _navBarSelectedItem = MutableStateFlow<BottomNavBarNavigation>(BottomNavBarNavigation.Discover)
    val navBarSelectedItem: StateFlow<BottomNavBarNavigation> = _navBarSelectedItem

    fun onNavBarItemChanged(item: BottomNavBarNavigation) {
        _navBarSelectedItem.value = item
    }
}