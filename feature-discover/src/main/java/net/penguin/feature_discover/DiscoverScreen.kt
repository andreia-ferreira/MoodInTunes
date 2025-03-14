package net.penguin.feature_discover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    val searchResults by viewModel.searchByMoodResult.collectAsStateWithLifecycle()

    Column(modifier) {
        Button(
            onClick = { viewModel.searchByMood("happy") }
        ) {
            Text("Click me")
        }

        if (searchResults.isNotEmpty()) {
            LazyColumn {
                items(searchResults) {
                    Text(it.name)
                }
            }
        }
    }
}

@Preview
@Composable
private fun DiscoverScreenPreview() {

}
