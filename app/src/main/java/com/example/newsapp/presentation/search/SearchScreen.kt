package com.example.newsapp.presentation.search

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.Dimens
import com.example.newsapp.presentation.common.ArticlesList
import com.example.newsapp.ui.theme.NewsAppTheme
import com.loc.newsapp.presentation.common.SearchBar

@Composable
fun SearchScreen(
    state: SearchState, event: (SearchEvent) -> Unit, navigateToDetail: (Article) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(
                top = Dimens.MediumPadding1,
                start = Dimens.MediumPadding1,
                end = Dimens.MediumPadding1
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        SearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = {
                event(SearchEvent.SearchNews)
            }
        )

        Spacer(modifier = Modifier.height(Dimens.MediumPadding1))

        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            ArticlesList(
                articles = articles,
                onClick = {
                    navigateToDetail(it)
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSearchScreen() {
    NewsAppTheme {

    }
}