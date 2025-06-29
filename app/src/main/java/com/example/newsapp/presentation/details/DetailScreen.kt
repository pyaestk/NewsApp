package com.example.newsapp.presentation.details

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.details.components.DetailsTopBar
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.presentation.Dimens
import com.example.newsapp.presentation.bookmark.BookmarkState
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.util.DataState
import com.example.newsapp.util.UIComponent

@Composable
fun DetailScreen(
    article: Article,
    event: (DetailEvent) -> Unit,
    navigateUp: () -> Unit,
    sideEffect: UIComponent?,
    bookmarkState: DataState<Unit>
){
    val context = LocalContext.current

    LaunchedEffect(key1 = sideEffect) {
        sideEffect?.let {
            when(sideEffect){
                is UIComponent.Toast ->{
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                    event(DetailEvent.RemoveSideEffect)
                }
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding()
    ) {
        DetailsTopBar(
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = article.url.toUri()

                    if (it.resolveActivity(context.packageManager) != null){
                        context.startActivity(it)
                    }
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, article.url)
                    it.type = "text/plain"

                    if (it.resolveActivity(context.packageManager) != null){
                        context.startActivity(it)
                    }
                }
            },
            onBookmarkClick = {
                event(DetailEvent.UpsertDeleteArticle(article = article))
            },
            onBackClick = navigateUp
        )


        if (bookmarkState is DataState.Loading && bookmarkState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp
                )
            ) {
                item {
                    AsyncImage(
                        model = ImageRequest
                            .Builder(context)
                            .data(article.urlToImage)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Article image",
                        modifier = Modifier.fillMaxWidth().height(Dimens.ArticleImageHeight).clip(
                            MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(Dimens.MediumPadding1))

                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.displaySmall,
                        color = colorResource(
                            id = R.color.text_title
                        )
                    )

                    Text(
                        text = article.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(
                            id = R.color.text_title
                        )
                    )
                }
            }

        }
    }

}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewDetailScreen(){
    NewsAppTheme {
        // Create a sample Article object for the preview
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)){
            val sampleArticle = Article(
                source = com.example.newsapp.domain.model.Source(id = "id", name = "Sample Source"),
                author = "John Doe",
                title = "Sample Article Title",
                description = "This is a sample article description for preview purposes.",
                url = "https://example.com/sample-article",
                urlToImage = "https://example.com/sample-image.jpg",
                publishedAt = "2023-01-01T12:00:00Z",
                content = "This is the full content of the sample article. It can be a bit longer to see how it renders in the UI."
            )
        }
    }
}