package com.example.newsapp.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.bookmark.BookmarkScreen
import com.example.newsapp.presentation.bookmark.BookmarkViewModel
import com.example.newsapp.presentation.details.DetailScreen
import com.example.newsapp.presentation.details.DetailViewModel
import com.example.newsapp.presentation.home.HomeScreen
import com.example.newsapp.presentation.home.HomeViewModel
import com.example.newsapp.presentation.search.SearchScreen
import com.example.newsapp.presentation.search.SearchViewModel

@Composable
fun NewsNavigator() {
    val bottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, label = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, label = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, label = "Bookmark"),
        )
    }

    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    selectedItem = when (backstackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookmarkScreen.route -> 2
        else -> 0
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NewsBottomNavigation(
                items = bottomNavigationItem,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTap(navController, Route.HomeScreen.route)
                        1 -> navigateToTap(navController, Route.SearchScreen.route)
                        2 -> navigateToTap(navController, Route.BookmarkScreen.route)
                    }
                }
            )
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTap(navController, Route.SearchScreen.route)
                    },
                    navigateToDetails = {
                        Log.d("TAG", "NewsNavigator: $it")
                        navigateToDetails(navController, it)
                    },
                )
            }

            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetail = {
                        navigateToDetails(navController, it)
                    }
                )
            }

            composable(route = Route.DetailsScreen.route) {
                val viewModel: DetailViewModel = hiltViewModel()
                navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")?.let { article ->
                    Log.d("TAG", "NewsNavigator: $article")
                    DetailScreen(
                        article = article,
                        event = viewModel::onEvent,
                        navigateUp = {
                            navController.navigateUp()
                        },
                        sideEffect = viewModel.sideEffect,
                        bookmarkState = viewModel.bookmarkState
                    )
                }
            }

            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(
                    state = state,
                    navigateToDetail = {
                        navigateToDetails(navController, it)
                    },
                )
            }

        }

    }
}

private fun navigateToTap(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let {
            popUpTo(it) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {

    navController.currentBackStackEntry?.savedStateHandle?.set(
        key = "article",
        value = article
    )
    navController.navigate(Route.DetailsScreen.route)
}