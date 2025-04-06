package net.penguin.moodintunes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import net.penguin.common_design.navigation.BottomNavBarNavigation
import net.penguin.common_design.navigation.PlaylistDetailNavigation
import net.penguin.common_design.theme.MoodInTunesTheme
import net.penguin.component_playlist.ui.PlaylistScreen
import net.penguin.feature_collection.CollectionScreen
import net.penguin.feature_discover.ui.DiscoverScreen
import net.penguin.moodintunes.ui.MoodInTunesNavBar

@AndroidEntryPoint
class MoodInTunesMainActivity : ComponentActivity() {
    private val viewModel: MoodInTunesMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val selectedNavigationItem by viewModel.navBarSelectedItem.collectAsStateWithLifecycle()
            val navController = rememberNavController()

            MoodInTunesTheme {
                Box(modifier = Modifier.fillMaxSize()) {
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
                            startDestination = BottomNavBarNavigation.Discover
                        ) {
                            composable<BottomNavBarNavigation.Discover> {
                                DiscoverScreen(
                                    modifier = Modifier.padding(innerPadding),
                                    goToPlaylistDetails = { id, isSaved ->
                                        navController.navigate(
                                            route = PlaylistDetailNavigation(
                                                id,
                                                isSaved
                                            )
                                        )
                                    }
                                )

                            }
                            composable<BottomNavBarNavigation.Collection> {
                                BackHandler {
                                    finish()
                                }
                                CollectionScreen(
                                    modifier = Modifier.padding(innerPadding),
                                    goToPlaylistDetails = { id, isSaved ->
                                        navController.navigate(
                                            route = PlaylistDetailNavigation(
                                                id,
                                                isSaved
                                            )
                                        )
                                    }
                                )
                            }
                            composable<PlaylistDetailNavigation> {
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
}