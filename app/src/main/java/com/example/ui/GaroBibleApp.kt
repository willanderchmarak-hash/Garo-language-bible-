package com.example.ui

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.Book
import com.example.data.Bookmark
import com.example.data.Verse
import kotlinx.coroutines.launch

enum class BibleTab(val title: String) {
    READER("Sastro"),
    SEARCH("Nia"),
    SAVED("Mona"),
    DEVOTIONAL("Pringni")
}

// Custom Colors for each theme
data class ReaderTheme(
    val background: Color,
    val onBackground: Color,
    val cardBackground: Color,
    val cardBorder: Color,
    val primaryAccent: Color,
    val textSecondary: Color
)

val SepiaTheme = ReaderTheme(
    background = Color(0xFFF4ECD8),
    onBackground = Color(0xFF2B1F12),
    cardBackground = Color(0xFFFAF5E8),
    cardBorder = Color(0xFFE8DBBD),
    primaryAccent = Color(0xFF8E5A2A),
    textSecondary = Color(0xFF6E563B)
)

val CreamTheme = ReaderTheme(
    background = Color(0xFFFAF9F2),
    onBackground = Color(0xFF1B1C1A),
    cardBackground = Color(0xFFFFFFFF),
    cardBorder = Color(0xFFEBEAE2),
    primaryAccent = Color(0xFF3B6846),
    textSecondary = Color(0xFF5A5C55)
)

val CharcoalTheme = ReaderTheme(
    background = Color(0xFF121212),
    onBackground = Color(0xFFE3E3E3),
    cardBackground = Color(0xFF1E1E1E),
    cardBorder = Color(0xFF2D2D2D),
    primaryAccent = Color(0xFFFFD54F),
    textSecondary = Color(0xFFAAAAAA)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GaroBibleApp(viewModel: GaroBibleViewModel) {
    val currentTab = remember { mutableStateOf(BibleTab.READER) }
    val selectedBook by viewModel.selectedBook.collectAsStateWithLifecycle()
    val selectedChapter by viewModel.selectedChapter.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()

    val readerTheme = when (themeMode) {
        "Cream" -> CreamTheme
        "Charcoal" -> CharcoalTheme
        else -> SepiaTheme // "Sepia" default
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = readerTheme.cardBackground,
                tonalElevation = 8.dp,
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                NavigationBarItem(
                    selected = currentTab.value == BibleTab.READER,
                    onClick = { currentTab.value = BibleTab.READER },
                    icon = {
                        Icon(
                            imageVector = if (currentTab.value == BibleTab.READER) Icons.Filled.MenuBook else Icons.Outlined.MenuBook,
                            contentDescription = "Reader",
                            tint = if (currentTab.value == BibleTab.READER) readerTheme.primaryAccent else readerTheme.textSecondary
                        )
                    },
                    label = {
                        Text(
                            text = "Sastro",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (currentTab.value == BibleTab.READER) readerTheme.primaryAccent else readerTheme.textSecondary
                        )
                    },
                    modifier = Modifier.testTag("tab_reader")
                )
                NavigationBarItem(
                    selected = currentTab.value == BibleTab.SEARCH,
                    onClick = { currentTab.value = BibleTab.SEARCH },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = if (currentTab.value == BibleTab.SEARCH) readerTheme.primaryAccent else readerTheme.textSecondary
                        )
                    },
                    label = {
                        Text(
                            text = "Nia",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (currentTab.value == BibleTab.SEARCH) readerTheme.primaryAccent else readerTheme.textSecondary
                        )
                    },
                    modifier = Modifier.testTag("tab_search")
                )
                NavigationBarItem(
                    selected = currentTab.value == BibleTab.SAVED,
                    onClick = { currentTab.value = BibleTab.SAVED },
                    icon = {
                        Icon(
                            imageVector = if (currentTab.value == BibleTab.SAVED) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Saved",
                            tint = if (currentTab.value == BibleTab.SAVED) readerTheme.primaryAccent else readerTheme.textSecondary
                        )
                    },
                    label = {
                        Text(
                            text = "Mona",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (currentTab.value == BibleTab.SAVED) readerTheme.primaryAccent else readerTheme.textSecondary
                        )
                    },
                    modifier = Modifier.testTag("tab_saved")
                )
                NavigationBarItem(
                    selected = currentTab.value == BibleTab.DEVOTIONAL,
                    onClick = { currentTab.value = BibleTab.DEVOTIONAL },
                    icon = {
                        Icon(
                            imageVector = if (currentTab.value == BibleTab.DEVOTIONAL) Icons.Filled.WbSunny else Icons.Outlined.WbSunny,
                            contentDescription = "Devotional",
                            tint = if (currentTab.value == BibleTab.DEVOTIONAL) readerTheme.primaryAccent else readerTheme.textSecondary
                        )
                    },
                    label = {
                        Text(
                            text = "Pringni",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (currentTab.value == BibleTab.DEVOTIONAL) readerTheme.primaryAccent else readerTheme.textSecondary
                        )
                    },
                    modifier = Modifier.testTag("tab_devotional")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(readerTheme.background)
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentTab.value,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "TabTransition"
            ) { targetTab ->
                when (targetTab) {
                    BibleTab.READER -> ReaderScreen(viewModel, readerTheme)
                    BibleTab.SEARCH -> SearchScreen(viewModel, readerTheme) { book, chapter ->
                        viewModel.selectBookAndChapter(book, chapter)
                        currentTab.value = BibleTab.READER
                    }
                    BibleTab.SAVED -> BookmarksScreen(viewModel, readerTheme) { book, chapter ->
                        viewModel.selectBookAndChapter(book, chapter)
                        currentTab.value = BibleTab.READER
                    }
                    BibleTab.DEVOTIONAL -> DevotionalScreen(viewModel, readerTheme)
                }
            }
        }
    }
}

@Composable
fun ReaderScreen(viewModel: GaroBibleViewModel, theme: ReaderTheme) {
    val selectedBook by viewModel.selectedBook.collectAsStateWithLifecycle()
    val selectedChapter by viewModel.selectedChapter.collectAsStateWithLifecycle()
    val currentVerses by viewModel.currentVerses.collectAsStateWithLifecycle()
    val fontSize by viewModel.fontSize.collectAsStateWithLifecycle()
    val isFetching by viewModel.isFetchingChapter.collectAsStateWithLifecycle()
    val fetchingError by viewModel.fetchingError.collectAsStateWithLifecycle()
    val allBooks by viewModel.allBooks.collectAsStateWithLifecycle()
    val bookmarks by viewModel.bookmarks.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()

    var showBookDialog by remember { mutableStateOf(false) }
    var showSettingsSheet by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    // Scroll to top when chapter changes
    LaunchedEffect(selectedBook, selectedChapter) {
        listState.scrollToItem(0)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Book & Chapter Header Selector
        Surface(
            color = theme.cardBackground,
            shadowElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Book Name Selector Trigger
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(theme.background)
                            .clickable { showBookDialog = true }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Books",
                            tint = theme.primaryAccent,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = selectedBook?.name ?: "Loading...",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.onBackground
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Select Book",
                            tint = theme.onBackground
                        )
                    }

                    // Settings & Style button
                    Row {
                        IconButton(
                            onClick = { showSettingsSheet = !showSettingsSheet },
                            modifier = Modifier.testTag("settings_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.FormatSize,
                                contentDescription = "Font settings",
                                tint = theme.primaryAccent
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Chapter Fast Navigator (Prev - Chapter Number - Next)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { viewModel.prevChapter() },
                        enabled = selectedChapter > 1,
                        modifier = Modifier
                            .background(theme.background, CircleShape)
                            .size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = "Previous Chapter",
                            tint = if (selectedChapter > 1) theme.primaryAccent else theme.textSecondary.copy(alpha = 0.4f)
                        )
                    }

                    // Chapter horizontal selection scroll or simple direct indicator
                    Text(
                        text = "Biap $selectedChapter",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.onBackground,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    IconButton(
                        onClick = { viewModel.nextChapter() },
                        enabled = selectedBook != null && selectedChapter < selectedBook!!.totalChapters,
                        modifier = Modifier
                            .background(theme.background, CircleShape)
                            .size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Next Chapter",
                            tint = if (selectedBook != null && selectedChapter < selectedBook!!.totalChapters) theme.primaryAccent else theme.textSecondary.copy(alpha = 0.4f)
                        )
                    }
                }
            }
        }

        // Main Content Area (Verses list or seeding/fetching indicators)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            if (isFetching) {
                // Beautiful dynamic loading state
                Card(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.cardBackground),
                    border = BorderStroke(1.dp, theme.cardBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = theme.primaryAccent)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "A·chik Sastroko sokatjolenga...",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.onBackground,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Gemini AI is fetching & translating ${selectedBook?.name} Chapter $selectedChapter completely offline for you.",
                            fontSize = 12.sp,
                            color = theme.textSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else if (currentVerses.isEmpty()) {
                // Offer AI loader if chapter is not seeded offline yet
                Card(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.cardBackground),
                    border = BorderStroke(1.dp, theme.cardBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudDownload,
                            contentDescription = "Offline Cache",
                            tint = theme.primaryAccent,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Biap $selectedChapter offline dongsrangja",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.onBackground,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "This chapter is not pre-loaded offline. Click below to fetch, translate, and cache this entire chapter via Gemini AI. Once cached, it is accessible 100% offline!",
                            fontSize = 13.sp,
                            color = theme.textSecondary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        if (fetchingError != null) {
                            Text(
                                text = "Gimatchi ong·ja: $fetchingError",
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 8.dp),
                                textAlign = TextAlign.Center
                            )
                        }

                        Button(
                            onClick = { viewModel.fetchCurrentChapterViaAI() },
                            colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().testTag("fetch_button")
                        ) {
                            Text("Gemini AI-chi Raka", color = if (theme == CharcoalTheme) Color.Black else Color.White)
                        }
                    }
                }
            } else {
                // Elegant Scripture reading list
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        // Book Banner Header inside list
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = selectedBook?.name?.uppercase() ?: "",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.Serif,
                                color = theme.primaryAccent,
                                modifier = Modifier.testTag("reader_book_title")
                            )
                            Text(
                                text = "Chapter $selectedChapter",
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Serif,
                                fontStyle = FontStyle.Italic,
                                color = theme.textSecondary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider(color = theme.primaryAccent.copy(alpha = 0.3f), thickness = 1.dp, modifier = Modifier.width(60.dp))
                        }
                    }

                    items(currentVerses) { verse ->
                        val isSaved = bookmarks.any { 
                            it.bookId == verse.bookId && it.chapterNumber == verse.chapterNumber && it.verseNumber == verse.verseNumber 
                        }
                        VerseRow(
                            verse = verse,
                            fontSize = fontSize,
                            theme = theme,
                            isSaved = isSaved,
                            onToggleBookmark = { viewModel.toggleBookmark(verse) }
                        )
                    }
                }
            }
        }
    }

    // Book Selection Dialog (Old & New Testament)
    if (showBookDialog) {
        BookSelectionDialog(
            allBooks = allBooks,
            theme = theme,
            onDismiss = { showBookDialog = false },
            onBookSelected = { book ->
                viewModel.selectBookAndChapter(book, 1)
                showBookDialog = false
            }
        )
    }

    // Text Style Options drawer
    if (showSettingsSheet) {
        Dialog(onDismissRequest = { showSettingsSheet = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = theme.cardBackground),
                border = BorderStroke(1.dp, theme.cardBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Reader Settings",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.onBackground
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Font Size option
                    Text(
                        text = "Kattar Dal·ani (Font Size): ${fontSize.toInt()}sp",
                        fontSize = 14.sp,
                        color = theme.onBackground
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { viewModel.updateFontSize(fontSize - 2f) }) {
                            Icon(Icons.Default.Remove, "Decrease", tint = theme.primaryAccent)
                        }
                        Slider(
                            value = fontSize,
                            onValueChange = { viewModel.updateFontSize(it) },
                            valueRange = 14f..28f,
                            modifier = Modifier.weight(1f),
                            colors = SliderDefaults.colors(
                                thumbColor = theme.primaryAccent,
                                activeTrackColor = theme.primaryAccent
                            )
                        )
                        IconButton(onClick = { viewModel.updateFontSize(fontSize + 2f) }) {
                            Icon(Icons.Default.Add, "Increase", tint = theme.primaryAccent)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Reading Theme Selectors
                    Text(
                        text = "Noksal Mikkang (Reading Mode Theme):",
                        fontSize = 14.sp,
                        color = theme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ThemeButton("Cream", themeMode == "Cream", CreamTheme, Modifier.weight(1f)) {
                            viewModel.setThemeMode("Cream")
                        }
                        ThemeButton("Sepia", themeMode == "Sepia", SepiaTheme, Modifier.weight(1f)) {
                            viewModel.setThemeMode("Sepia")
                        }
                        ThemeButton("Charcoal", themeMode == "Charcoal", CharcoalTheme, Modifier.weight(1f)) {
                            viewModel.setThemeMode("Charcoal")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(
                        onClick = { showSettingsSheet = false },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Makat", color = theme.primaryAccent)
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeButton(
    label: String,
    isSelected: Boolean,
    btnTheme: ReaderTheme,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(44.dp)
            .clickable { onClick() },
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) btnTheme.primaryAccent else btnTheme.cardBorder
        ),
        colors = CardDefaults.cardColors(containerColor = btnTheme.background)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = btnTheme.onBackground
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerseRow(
    verse: Verse,
    fontSize: Float,
    theme: ReaderTheme,
    isSaved: Boolean,
    onToggleBookmark: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .combinedClickable(
                onClick = { /* Tap selection action can be added */ },
                onLongClick = {
                    clipboardManager.setText(AnnotatedString("${verse.bookName} ${verse.chapterNumber}:${verse.verseNumber} - ${verse.text}"))
                    // Copied alert
                }
            )
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Verse Number
        Text(
            text = verse.verseNumber.toString(),
            fontSize = (fontSize - 2).sp,
            fontWeight = FontWeight.Bold,
            color = theme.primaryAccent,
            modifier = Modifier
                .width(28.dp)
                .padding(top = 2.dp),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Verse Text
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = verse.text,
                fontSize = fontSize.sp,
                fontFamily = FontFamily.Serif,
                lineHeight = (fontSize * 1.5).sp,
                color = theme.onBackground
            )
        }

        // Bookmark toggle icon
        IconButton(
            onClick = onToggleBookmark,
            modifier = Modifier.size(32.dp).testTag("bookmark_verse_${verse.verseNumber}")
        ) {
            Icon(
                imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription = "Save verse",
                tint = if (isSaved) theme.primaryAccent else theme.textSecondary.copy(alpha = 0.5f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun BookSelectionDialog(
    allBooks: List<Book>,
    theme: ReaderTheme,
    onDismiss: () -> Unit,
    onBookSelected: (Book) -> Unit
) {
    var selectedTab by remember { mutableStateOf("New") } // "Old" or "New"

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = theme.background),
            border = BorderStroke(1.dp, theme.cardBorder)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Selector Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(theme.cardBackground)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sastro Kitaprang (Books)",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.onBackground,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, "Close", tint = theme.onBackground)
                    }
                }

                // Testament Tab Row
                TabRow(
                    selectedTabIndex = if (selectedTab == "Old") 0 else 1,
                    containerColor = theme.cardBackground,
                    contentColor = theme.primaryAccent
                ) {
                    Tab(
                        selected = selectedTab == "Old",
                        onClick = { selectedTab = "Old" },
                        text = { Text("Niam Gitcham (Old)", fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = selectedTab == "New",
                        onClick = { selectedTab = "New" },
                        text = { Text("Niam Gital (New)", fontWeight = FontWeight.Bold) }
                    )
                }

                val filteredBooks = allBooks.filter { it.testament == selectedTab }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(filteredBooks) { book ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onBookSelected(book) }
                                .testTag("book_item_${book.name}"),
                            colors = CardDefaults.cardColors(containerColor = theme.cardBackground),
                            border = BorderStroke(1.dp, theme.cardBorder)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = book.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = theme.onBackground
                                    )
                                    Text(
                                        text = book.englishName,
                                        fontSize = 12.sp,
                                        color = theme.textSecondary
                                    )
                                }
                                Badge(
                                    containerColor = theme.background,
                                    contentColor = theme.primaryAccent,
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Text("${book.totalChapters} Biap", modifier = Modifier.padding(4.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchScreen(
    viewModel: GaroBibleViewModel,
    theme: ReaderTheme,
    onVerseSelected: (Book, Int) -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val allBooks by viewModel.allBooks.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Am·bo (Search)",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = theme.onBackground,
            fontFamily = FontFamily.Serif
        )
        Text(
            text = "Search offline Garo bible verses instantly",
            fontSize = 13.sp,
            color = theme.textSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search text field
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_input"),
            placeholder = { Text("E.g. Maina Isol, Jihova...", color = theme.textSecondary) },
            leadingIcon = { Icon(Icons.Default.Search, "Search", tint = theme.primaryAccent) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                        Icon(Icons.Default.Clear, "Clear", tint = theme.onBackground)
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = theme.primaryAccent,
                unfocusedBorderColor = theme.cardBorder,
                focusedContainerColor = theme.cardBackground,
                unfocusedContainerColor = theme.cardBackground,
                focusedTextColor = theme.onBackground,
                unfocusedTextColor = theme.onBackground
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (searchQuery.length < 2) {
            // Friendly Tip empty state
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search tip",
                        tint = theme.textSecondary.copy(alpha = 0.5f),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Am·na gita kattako chipbo",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.onBackground
                    )
                    Text(
                        text = "Type at least 2 characters to search across cached scripture",
                        fontSize = 12.sp,
                        color = theme.textSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }
        } else if (searchResults.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.FindInPage,
                        contentDescription = "No results",
                        tint = theme.textSecondary.copy(alpha = 0.5f),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Mamungba nikja",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.onBackground
                    )
                    Text(
                        text = "No offline verses match '$searchQuery'. Keep in mind search only filters currently cached offline books.",
                        fontSize = 12.sp,
                        color = theme.textSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }
        } else {
            // Results list
            Text(
                text = "${searchResults.size} results found",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = theme.primaryAccent,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(searchResults) { verse ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val targetBook = allBooks.find { it.id == verse.bookId }
                                if (targetBook != null) {
                                    onVerseSelected(targetBook, verse.chapterNumber)
                                }
                            }
                            .testTag("search_result_${verse.bookName}_${verse.verseNumber}"),
                        colors = CardDefaults.cardColors(containerColor = theme.cardBackground),
                        border = BorderStroke(1.dp, theme.cardBorder)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${verse.bookName} ${verse.chapterNumber}:${verse.verseNumber}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.primaryAccent
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "Go to verse",
                                    tint = theme.textSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = verse.text,
                                fontSize = 14.sp,
                                fontFamily = FontFamily.Serif,
                                color = theme.onBackground,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookmarksScreen(
    viewModel: GaroBibleViewModel,
    theme: ReaderTheme,
    onBookmarkClicked: (Book, Int) -> Unit
) {
    val bookmarks by viewModel.bookmarks.collectAsStateWithLifecycle()
    val allBooks by viewModel.allBooks.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Mona (Saved)",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = theme.onBackground,
            fontFamily = FontFamily.Serif
        )
        Text(
            text = "Your saved and bookmarked bible verses",
            fontSize = 13.sp,
            color = theme.textSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (bookmarks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.BookmarkBorder,
                        contentDescription = "No bookmarks",
                        tint = theme.textSecondary.copy(alpha = 0.5f),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Bookmark gri",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.onBackground
                    )
                    Text(
                        text = "You haven't saved any verses yet. Save verses by tapping the bookmark icon in the scripture reader.",
                        fontSize = 12.sp,
                        color = theme.textSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(bookmarks) { bookmark ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val targetBook = allBooks.find { it.id == bookmark.bookId }
                                if (targetBook != null) {
                                    onBookmarkClicked(targetBook, bookmark.chapterNumber)
                                }
                            }
                            .testTag("bookmark_item_${bookmark.bookName}_${bookmark.verseNumber}"),
                        colors = CardDefaults.cardColors(containerColor = theme.cardBackground),
                        border = BorderStroke(1.dp, theme.cardBorder)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${bookmark.bookName} ${bookmark.chapterNumber}:${bookmark.verseNumber}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.primaryAccent
                                )
                                Row {
                                    IconButton(
                                        onClick = {
                                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                                type = "text/plain"
                                                putExtra(Intent.EXTRA_SUBJECT, "Garo Bible Verse")
                                                putExtra(Intent.EXTRA_TEXT, "${bookmark.verseText}\n\n— ${bookmark.bookName} ${bookmark.chapterNumber}:${bookmark.verseNumber}")
                                            }
                                            context.startActivity(Intent.createChooser(shareIntent, "Share Verse"))
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.Share, "Share", tint = theme.primaryAccent, modifier = Modifier.size(18.dp))
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    IconButton(
                                        onClick = { viewModel.deleteBookmark(bookmark) },
                                        modifier = Modifier.size(32.dp).testTag("delete_bookmark_${bookmark.verseNumber}")
                                    ) {
                                        Icon(Icons.Default.Delete, "Delete", tint = Color.Red.copy(alpha = 0.8f), modifier = Modifier.size(18.dp))
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = bookmark.verseText,
                                fontSize = 15.sp,
                                fontFamily = FontFamily.Serif,
                                color = theme.onBackground
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DevotionalScreen(viewModel: GaroBibleViewModel, theme: ReaderTheme) {
    val devotional by viewModel.dailyDevotional.collectAsStateWithLifecycle()
    val isGenerating by viewModel.isGeneratingDevotional.collectAsStateWithLifecycle()
    val error by viewModel.devotionalError.collectAsStateWithLifecycle()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Pringni (Daily Devotional)",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = theme.onBackground,
            fontFamily = FontFamily.Serif
        )
        Text(
            text = "Start your morning with spiritual encouragement in Garo",
            fontSize = 13.sp,
            color = theme.textSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isGenerating) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = theme.primaryAccent)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Pringni Kattarangko gapatjolenga...",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.onBackground
                    )
                    Text(
                        text = "Gemini AI is generating an inspiring custom meditation and prayer in Garo...",
                        fontSize = 12.sp,
                        color = theme.textSecondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp, start = 24.dp, end = 24.dp)
                    )
                }
            }
        } else if (devotional == null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.cardBackground),
                    border = BorderStroke(1.dp, theme.cardBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = "Sunshine",
                            tint = theme.primaryAccent,
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Da·alni Pringni Katta",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No devotional is cached for today yet. Use Gemini AI to generate a beautiful scripture reflection, devotional lesson, and prayer in the Garo language.",
                            fontSize = 13.sp,
                            color = theme.textSecondary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        if (error != null) {
                            Text(
                                text = "Gimatchi ong·ja: $error",
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 8.dp),
                                textAlign = TextAlign.Center
                            )
                        }

                        Button(
                            onClick = { viewModel.generateTodayDevotional() },
                            colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().testTag("generate_devotional_button")
                        ) {
                            Text("Generate Devotional (Garo)", color = if (theme == CharcoalTheme) Color.Black else Color.White)
                        }
                    }
                }
            }
        } else {
            // Display beautiful custom Daily Devotional
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = theme.cardBackground),
                        border = BorderStroke(1.dp, theme.cardBorder)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            // Devotional Badge & Title
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Badge(
                                    containerColor = theme.background,
                                    contentColor = theme.primaryAccent,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                ) {
                                    Text("DA·ALNI KATTA", fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp))
                                }
                                Text(
                                    text = devotional?.date ?: "",
                                    fontSize = 12.sp,
                                    color = theme.textSecondary,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Text(
                                text = devotional?.title ?: "",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.onBackground,
                                fontFamily = FontFamily.Serif
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Highlighted Verse Box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(theme.background)
                                    .padding(16.dp)
                            ) {
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.FormatQuote,
                                            contentDescription = "Quote",
                                            tint = theme.primaryAccent,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "${devotional?.bookName} ${devotional?.chapterNumber}:${devotional?.verseNumber}",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = theme.primaryAccent
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = devotional?.verseText ?: "",
                                        fontSize = 15.sp,
                                        fontFamily = FontFamily.Serif,
                                        fontStyle = FontStyle.Italic,
                                        color = theme.onBackground,
                                        lineHeight = 22.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Devotional Meditation Text
                            Text(
                                text = devotional?.devotionalText ?: "",
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Serif,
                                color = theme.onBackground,
                                lineHeight = 24.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))
                            Divider(color = theme.cardBorder, thickness = 1.dp)
                            Spacer(modifier = Modifier.height(16.dp))

                            // Devotional Prayer Header
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = "Prayer",
                                    tint = theme.primaryAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Bi·ani (Prayer)",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = theme.primaryAccent
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))

                            // Prayer Text
                            Text(
                                text = devotional?.prayer ?: "",
                                fontSize = 15.sp,
                                fontStyle = FontStyle.Italic,
                                fontFamily = FontFamily.Serif,
                                color = theme.onBackground,
                                lineHeight = 22.sp
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Share Devotional action
                            Button(
                                onClick = {
                                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_SUBJECT, "Garo Daily Devotional")
                                        putExtra(Intent.EXTRA_TEXT, "${devotional?.title}\n\n${devotional?.verseText}\n— ${devotional?.bookName} ${devotional?.chapterNumber}:${devotional?.verseNumber}\n\nMeditation: ${devotional?.devotionalText}\n\nPrayer: ${devotional?.prayer}")
                                    }
                                    context.startActivity(Intent.createChooser(shareIntent, "Share Devotional"))
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = theme.primaryAccent),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Share, contentDescription = "Share", modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Devotional-ko Share Ka·na", color = if (theme == CharcoalTheme) Color.Black else Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}
