package com.example.newsapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.Dimens
import com.loc.newsapp.presentation.common.EmptyScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
) {
    if (onRefresh != null) {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = modifier
        ) {
            ListContent(articles, onClick, modifier, onRefresh)
        }
    } else {

        ListContent(articles, onClick, modifier, onRefresh)
    }
}

@Composable
private fun ListContent(
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
    onRefresh: (() -> Unit)? = null
) {
    val handlePagingResource = handlePagingResult(articles, onRefresh)
    if (handlePagingResource) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
            contentPadding = PaddingValues(all = Dimens.ExtraSmallPadding2)
        ) {
            items(count = articles.itemCount) {
                articles[it]?.let { article ->
                    ArticleCard(article = article, onClick = { onClick(article) })
                }
            }
        }
    }
}


@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit
) {

    if (articles.isEmpty()){
        EmptyScreen()
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
        contentPadding = PaddingValues(all = Dimens.ExtraSmallPadding2)
    ) {
        items(count = articles.size) {
            val article = articles[it]
            ArticleCard(article = article, onClick = { onClick(article) })
        }
    }
}


@Composable
fun handlePagingResult(
    articles: LazyPagingItems<Article>,
    onRefresh: (() -> Unit)? = null
): Boolean{
    val loadState = articles.loadState
    val error = when{
        loadState.refresh is LoadState.Error -> {
            loadState.refresh as LoadState.Error
        }
        loadState.prepend is LoadState.Error -> {
            loadState.prepend as LoadState.Error
        }
        loadState.append is LoadState.Error -> {
            loadState.append as LoadState.Error
        }
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }
        error != null -> {
            EmptyScreen(error = error, onRefresh = onRefresh)
            false
        }
        articles.itemCount < 1 -> {
            EmptyScreen(onRefresh = onRefresh)
            false
        }
        else -> true
    }
}


@Composable
private fun ShimmerEffect(){
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1)
    ) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier
            )
        }
    }
}