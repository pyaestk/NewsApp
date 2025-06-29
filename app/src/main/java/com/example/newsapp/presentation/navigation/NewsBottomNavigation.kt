package com.example.newsapp.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.R
import com.example.newsapp.presentation.Dimens
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsBottomNavigation(
    items: List<BottomNavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 10.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onItemClick(index) },
                icon = {
                    Column(
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.label,
                            modifier = Modifier.size(Dimens.IconSize)
                        )
                        Spacer(modifier = Modifier.height(Dimens.ExtraSmallPadding2))
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelSmall
                        )

                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }

}

data class BottomNavigationItem(
    @DrawableRes
    val icon: Int,
    val label: String,
)


@Composable
@Preview(showBackground = true)

fun PreviewNewsBottomNavigation(){
    NewsAppTheme {
        NewsBottomNavigation(
            items = listOf(
                BottomNavigationItem(icon = R.drawable.ic_home, label = "Home"),
                BottomNavigationItem(icon = R.drawable.ic_search, label = "Search"),
                BottomNavigationItem(icon = R.drawable.ic_bookmark, label = "Bookmark"),
            ),
            selectedItem = 0,
            onItemClick = {  }
        )
    }

}