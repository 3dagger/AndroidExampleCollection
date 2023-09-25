package kr.dagger.composetablayout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.reflect.ComposableMethod
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TabItem(
	var icon: ImageVector,
	var title: String,
	var screen: @Composable () -> Unit
) {
	object Music : TabItem(Icons.Rounded.Call, "Music", { MusicScreen() })
	object Movies : TabItem(Icons.Rounded.Info, "Movies", { MovieScreen() })
	object Books : TabItem(Icons.Rounded.AccountBox, "Books", { BooksScreen() })
}
