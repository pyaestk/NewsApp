package com.example.newsapp.domain.model

// Dummy data for previews
val dummyArticles = listOf(
    Article(
        author = "John Doe",
        title = "Lorem Ipsum Dolor Sit Amet",
        description = "Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        url = "https://example.com/news1",
        urlToImage = "https://example.com/image1.jpg", // Replace with a placeholder image URL or null
        publishedAt = "2023-10-27T10:00:00Z",
        content = "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        source = Source(id = "example-news", name = "Example News")
    ),
    Article(
        author = "Jane Smith",
        title = "Sed Ut Perspiciatis Unde Omnis Iste Natus",
        description = "Error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.",
        url = "https://example.com/news2",
        urlToImage = "https://example.com/image2.jpg", // Replace with a placeholder image URL or null
        publishedAt = "2023-10-26T14:30:00Z",
        content = "Eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.",
        source = Source(id = "another-news", name = "Another News Source")
    ),
    // Add more dummy articles as needed
    Article(
        author = "Alice Brown",
        title = "Nemo Enim Ipsam Voluptatem Quia Voluptas",
        description = "Sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.",
        url = "https://example.com/news3",
        urlToImage = "", // Example with no image
        publishedAt = "2023-10-25T09:15:00Z",
        content = "Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit.",
        source = Source(id = "example-news", name = "Example News")
    )
)