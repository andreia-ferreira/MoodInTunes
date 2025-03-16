@file:OptIn(ExperimentalMaterial3Api::class)

package net.penguin.moodintunes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import net.penguin.common_design.theme.MoodInTunesTheme
import net.penguin.feature_discover.navigation.DiscoverNavigationItem
import net.penguin.feature_discover.ui.DiscoverScreen
import net.penguin.feature_discover.ui.PlaylistScreen
import net.penguin.moodintunes.navigation.MainNavigationItem
import net.penguin.moodintunes.ui.MoodInTunesNavBar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MoodInTunesMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val selectedNavigationItem by viewModel.navBarSelectedItem.collectAsStateWithLifecycle()
            val navController = rememberNavController()

            MoodInTunesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MoodInTunesNavBar(
                            selectedItem = selectedNavigationItem,
                            onItemSelected = {
                                viewModel.onNavBarItemChanged(it)
                                navController.navigate(it)
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = MainNavigationItem.Discover
                    ) {
                        composable<MainNavigationItem.Discover> {
                            DiscoverScreen(
                                modifier = Modifier.padding(innerPadding),
                                goToPlaylistDetails = {
                                    navController.navigate(route = DiscoverNavigationItem.PlaylistDetail(it))
                                }
                            )
                        }
                        composable<MainNavigationItem.Collection> {
                            Text(modifier = Modifier.padding(innerPadding), text = "hello")
                        }
                        composable<DiscoverNavigationItem.PlaylistDetail> {
                            PlaylistScreen(
                                onBackClicked = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}