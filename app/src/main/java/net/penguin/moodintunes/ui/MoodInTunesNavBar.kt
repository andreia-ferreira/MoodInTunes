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
import net.penguin.moodintunes.R
import net.penguin.moodintunes.navigation.MainNavigationItem

@Composable
fun MoodInTunesNavBar(
    modifier: Modifier = Modifier,
    selectedItem: MainNavigationItem,
    onItemSelected: (MainNavigationItem) -> Unit
) {
    NavigationBar(modifier) {
        NavigationBarItem(
            selected = selectedItem == MainNavigationItem.Discover,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.navigation_discover)
                )
            },
            label = {
                Text(text = stringResource(R.string.navigation_discover))
            },
            onClick = { onItemSelected(MainNavigationItem.Discover) }
        )
        NavigationBarItem(
            selected = selectedItem == MainNavigationItem.Collection,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.navigation_collection)
                )
            },
            label = {
                Text(text = stringResource(R.string.navigation_collection))
            },
            onClick = { onItemSelected(MainNavigationItem.Collection) }
        )
    }
}