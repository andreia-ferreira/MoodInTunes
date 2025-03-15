@file:OptIn(ExperimentalMaterial3Api::class)

package net.penguin.moodintunes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import net.penguin.common_design.theme.MoodInTunesTheme
import net.penguin.feature_discover.navigation.NavigationItem
import net.penguin.feature_discover.ui.DiscoverScreen
import net.penguin.feature_discover.ui.PlaylistScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoodInTunesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NavigationItem.Discover
                    ) {
                        composable<NavigationItem.Discover> {
                            DiscoverScreen(
                                modifier = Modifier.padding(innerPadding),
                                goToPlaylistDetails = {
                                    navController.navigate(route = NavigationItem.PlaylistDetail(it))
                                }
                            )
                        }
                        composable<NavigationItem.PlaylistDetail> {
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