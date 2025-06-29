package com.example.newsapp.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.Dimens
import com.example.newsapp.presentation.common.ArticlesList
import com.loc.newsapp.presentation.common.SearchBar

@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Article) -> Unit
) {
    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items.slice(
                    IntRange(
                        start = 0, endInclusive = 9
                    )
                ).joinToString(separator = " | ") { it.title }
            } else {
                ""
            }
        }
    }

    val isRefreshing = articles.loadState.refresh is LoadState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = Dimens.MediumPadding1,
                start = Dimens.MediumPadding1,
                end = Dimens.MediumPadding1
            )
            .statusBarsPadding()
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(30.dp)
        )

        Spacer(modifier = Modifier.padding(top = Dimens.MediumPadding1))

        SearchBar(
            text = "",
            readOnly = true,
            onValueChange = {},
            onClick = { navigateToSearch() },
            onSearch = {}
        )

        Spacer(modifier = Modifier.padding(top = Dimens.MediumPadding1))

        Text(
            text = titles,
            modifier = Modifier
                .fillMaxWidth()
                .basicMarquee(),
            fontSize = 12.sp,
            color = colorResource(id = R.color.placeholder)
        )

        Spacer(modifier = Modifier.padding(top = Dimens.MediumPadding1))

        ArticlesList(
            modifier = Modifier,
            articles = articles,
            onClick = {
                Log.d("TAG", "Article: $it")
                navigateToDetails(it)
            },
            isRefreshing = isRefreshing,
            onRefresh = { articles.refresh() }
        )

    }

}