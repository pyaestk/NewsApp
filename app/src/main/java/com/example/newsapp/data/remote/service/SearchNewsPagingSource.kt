package com.example.newsapp.data.remote.service

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.domain.model.Article

class SearchNewsPagingSource(
    private val searchQuery: String,
    private val newsApi: NewsApi,
    private val sources: String
): PagingSource<Int, Article>() {

    private var totalNewsCount = 0

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            val anchorPage = state.closestPageToPosition(anchorPosition = anchorPos)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {

            val newsResponse = newsApi.searchNews(sources = sources, page = page, searchQuery = searchQuery)

            totalNewsCount += newsResponse.articles.size

            val articles = newsResponse.articles.distinctBy { it.title }

            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
                prevKey = null
            )

        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }
}