package kr.dagger.composetablayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.LeadingIconTab
import androidx.compose.material.Scaffold
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MainScreen()
		}
	}
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen() {
	val tabs = listOf(
		TabItem.Music,
		TabItem.Movies,
		TabItem.Books
	)
	val pagerState = rememberPagerState()
	Scaffold(
		topBar = { TopBar() },
	) { padding ->
		Column(modifier = Modifier.padding(padding)) {
			Tabs(tabs = tabs, pagerState = pagerState)
			TabsContent(tabs = tabs, pagerState = pagerState)
		}
	}
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState) {
	HorizontalPager(state = pagerState, count = tabs.size) { page ->
		tabs[page].screen()
	}
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {
	val scope = rememberCoroutineScope()
	TabRow(
		selectedTabIndex = pagerState.currentPage,
		backgroundColor = Color.DarkGray,
		contentColor = Color.White,
		indicator = { tabPositions ->
			TabRowDefaults.Indicator(
				modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
			)
		}
	) {
		tabs.forEachIndexed { index, tab ->
			LeadingIconTab(
				selected = pagerState.currentPage == index,
				onClick = {
					scope.launch {
						pagerState.animateScrollToPage(index)
					}
				},
				text = { Text(text = tab.title, color = Color.White) },
				icon = { Icon(imageVector = tab.icon, contentDescription = "") })
		}
	}
}

@Composable
fun TopBar() {
	TopAppBar(
		title = { Text(text = stringResource(id = R.string.app_name), fontSize = 20.sp) },
		backgroundColor = Color.DarkGray,
		contentColor = Color.White
	)
}

@Composable
fun MusicScreen() {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(Color.DarkGray)
			.wrapContentSize(Alignment.Center)
	) {
		Text(
			text = "Music View",
			fontWeight = FontWeight.Bold,
			color = Color.White,
			modifier = Modifier.align(Alignment.CenterHorizontally),
			textAlign = TextAlign.Center,
			fontSize = 25.sp
		)
	}
}

@Composable
fun MovieScreen() {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(Color.DarkGray)
			.wrapContentSize(Alignment.Center)
	) {
		Text(
			text = "Movies View",
			fontWeight = FontWeight.Bold,
			color = Color.White,
			modifier = Modifier.align(Alignment.CenterHorizontally),
			textAlign = TextAlign.Center,
			fontSize = 25.sp
		)
	}
}

@Composable
fun BooksScreen() {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(Color.DarkGray)
			.wrapContentSize(Alignment.Center)
	) {
		Text(
			text = "Books View",
			fontWeight = FontWeight.Bold,
			color = Color.White,
			modifier = Modifier.align(Alignment.CenterHorizontally),
			textAlign = TextAlign.Center,
			fontSize = 25.sp
		)
	}
}

@Preview(showBackground = true)
@Composable
fun MusicScreenPreview() {
	MusicScreen()
}

@Preview(showBackground = true)
@Composable
fun MovieScreenPreview() {
	MovieScreen()
}

@Preview(showBackground = true)
@Composable
fun BooksScreenPreview() {
	BooksScreen()
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
	TopBar()
}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun TabsPreview() {
	val tabs = listOf(
		TabItem.Music,
		TabItem.Movies,
		TabItem.Books
	)
	val pagerState = rememberPagerState()
	Tabs(tabs = tabs, pagerState = pagerState)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
	MainScreen()
}