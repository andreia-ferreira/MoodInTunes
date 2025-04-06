package net.penguin.feature_discover.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.penguin.domain.entity.Playlist
import net.penguin.feature_discover.DiscoverViewModel
import net.penguin.feature_discover.R
import net.penguin.feature_discover.model.DiscoverScreenState

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    viewModel: DiscoverViewModel = hiltViewModel(),
    goToPlaylistDetails: (Long, Boolean) -> Unit,
) {
    val screenState by viewModel.discoverScreenState.collectAsStateWithLifecycle()
    var showPermissionErrorPopup by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            showPermissionErrorPopup = true
        }
    }

    Box(modifier.fillMaxSize()) {
        DiscoverScreenContent(
            screenState = screenState,
            onAction = {
                when (it) {
                    is DiscoverScreenAction.OnMoodSelected -> viewModel.onMoodSelected(
                        mood = it.mood,
                        query = it.name
                    )

                    is DiscoverScreenAction.OnMoodUnselected -> viewModel.onMoodUnselected()
                    is DiscoverScreenAction.OnSearchResultClicked -> goToPlaylistDetails(
                        it.playlist.id,
                        it.playlist.isSaved
                    )

                    is DiscoverScreenAction.OnEndOfListReached -> viewModel.onEndOfListReached(
                        currentQuery = it.currentQuery,
                        currentIndex = it.currentIndex
                    )

                    DiscoverScreenAction.OnReadTheRoomClicked -> {
                        when (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)) {
                            PackageManager.PERMISSION_GRANTED -> {

                            }
                            else -> {
                                launcher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    }
                }
            }
        )

        if (showPermissionErrorPopup) {
            AlertDialog(
                onDismissRequest = { showPermissionErrorPopup = false },
                title = {
                    Text(text = stringResource(R.string.error))
                },
                text = {
                    Text(text = stringResource(R.string.camera_permission_not_granted))
                },
                confirmButton = {
                    Button(onClick = {
                        showPermissionErrorPopup = false
                    }) {
                        Text(text = stringResource(R.string.ok))
                    }
                },
            )
        }
    }
}

sealed interface DiscoverScreenAction {
    data class OnMoodSelected(val mood: DiscoverScreenState.MoodItem, val name: String): DiscoverScreenAction
    data object OnMoodUnselected: DiscoverScreenAction
    data class OnSearchResultClicked(val playlist: Playlist): DiscoverScreenAction
    data class OnEndOfListReached(val currentQuery: String, val currentIndex: Int): DiscoverScreenAction
    data object OnReadTheRoomClicked: DiscoverScreenAction
}
