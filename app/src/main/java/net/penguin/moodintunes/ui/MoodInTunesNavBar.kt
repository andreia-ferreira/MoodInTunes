package net.penguin.moodintunes.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.penguin.common_design.navigation.BottomNavBarNavigation
import net.penguin.moodintunes.R

@Composable
fun MoodInTunesNavBar(
    modifier: Modifier = Modifier,
    selectedItem: BottomNavBarNavigation,
    onItemSelected: (BottomNavBarNavigation) -> Unit
) {
    NavigationBar(modifier) {
        NavigationBarItem(
            selected = selectedItem == BottomNavBarNavigation.Discover,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.navigation_discover)
                )
            },
            label = {
                Text(text = stringResource(R.string.navigation_discover))
            },
            onClick = { onItemSelected(BottomNavBarNavigation.Discover) }
        )
        NavigationBarItem(
            selected = selectedItem == BottomNavBarNavigation.Collection,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.navigation_collection)
                )
            },
            label = {
                Text(text = stringResource(R.string.navigation_collection))
            },
            onClick = { onItemSelected(BottomNavBarNavigation.Collection) }
        )
    }
}