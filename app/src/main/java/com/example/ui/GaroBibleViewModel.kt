package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.data.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GaroBibleViewModel(
    application: Application,
    private val repository: GaroBibleRepository
) : AndroidViewModel(application) {

    // 1. App initialization & Seeding
    init {
        viewModelScope.launch {
            repository.seedDatabase()
            // Set initial book as Johan (John - Book ID 43) or Matthew (Book ID 40)
            val initialBook = repository.getBookById(43)
            _selectedBook.value = initialBook
            _selectedChapter.value = 1
        }
    }

    val allBooks: StateFlow<List<Book>> = repository.allBooks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarks: StateFlow<List<Bookmark>> = repository.allBookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 2. Reading state
    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook: StateFlow<Book?> = _selectedBook.asStateFlow()

    private val _selectedChapter = MutableStateFlow(1)
    val selectedChapter: StateFlow<Int> = _selectedChapter.asStateFlow()

    // 3. Customize Reader Theme & Style
    private val _fontSize = MutableStateFlow(18f)
    val fontSize: StateFlow<Float> = _fontSize.asStateFlow()

    private val _themeMode = MutableStateFlow("Sepia") // "Cream", "Sepia", "Charcoal"
    val themeMode: StateFlow<String> = _themeMode.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val currentVerses: StateFlow<List<Verse>> = combine(
        _selectedBook,
        _selectedChapter
    ) { book, chapter ->
        Pair(book, chapter)
    }.flatMapLatest { (book, chapter) ->
        if (book != null) {
            repository.getVerses(book.id, chapter)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 4. AIS-powered Chapter Fetcher state
    private val _isFetchingChapter = MutableStateFlow(false)
    val isFetchingChapter: StateFlow<Boolean> = _isFetchingChapter.asStateFlow()

    private val _fetchingError = MutableStateFlow<String?>(null)
    val fetchingError: StateFlow<String?> = _fetchingError.asStateFlow()

    fun selectBookAndChapter(book: Book, chapter: Int) {
        _selectedBook.value = book
        val targetChapter = chapter.coerceIn(1, book.totalChapters)
        _selectedChapter.value = targetChapter
        _fetchingError.value = null
    }

    fun nextChapter() {
        val book = _selectedBook.value ?: return
        if (_selectedChapter.value < book.totalChapters) {
            _selectedChapter.value += 1
            _fetchingError.value = null
        }
    }

    fun prevChapter() {
        if (_selectedChapter.value > 1) {
            _selectedChapter.value -= 1
            _fetchingError.value = null
        }
    }

    fun updateFontSize(newSize: Float) {
        _fontSize.value = newSize.coerceIn(14f, 28f)
    }

    fun setThemeMode(mode: String) {
        _themeMode.value = mode
    }

    fun fetchCurrentChapterViaAI() {
        val book = _selectedBook.value ?: return
        val chapter = _selectedChapter.value
        _isFetchingChapter.value = true
        _fetchingError.value = null

        viewModelScope.launch {
            val apiKey = BuildConfig.GEMINI_API_KEY
            val result = repository.fetchAndCacheChapter(book.id, book.name, chapter, apiKey)
            _isFetchingChapter.value = false
            if (result.isFailure) {
                _fetchingError.value = result.exceptionOrNull()?.message ?: "Unknown error"
            }
        }
    }

    // 5. Search state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<List<Verse>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.length >= 2) {
                repository.searchVerses(query)
            } else {
                flowOf(emptyList())
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // 6. Bookmarking
    fun toggleBookmark(verse: Verse) {
        viewModelScope.launch {
            // Check if it already exists in bookmarks list
            val existing = bookmarks.value.find { 
                it.bookId == verse.bookId && it.chapterNumber == verse.chapterNumber && it.verseNumber == verse.verseNumber 
            }
            if (existing != null) {
                repository.removeBookmark(verse.bookId, verse.chapterNumber, verse.verseNumber)
            } else {
                repository.addBookmark(
                    Bookmark(
                        bookId = verse.bookId,
                        bookName = verse.bookName,
                        chapterNumber = verse.chapterNumber,
                        verseNumber = verse.verseNumber,
                        verseText = verse.text
                    )
                )
            }
        }
    }

    fun deleteBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.removeBookmark(bookmark.bookId, bookmark.chapterNumber, bookmark.verseNumber)
        }
    }

    // 7. Daily Devotional state
    val dailyDevotional: StateFlow<Devotional?> = repository.getDevotionalForToday()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _isGeneratingDevotional = MutableStateFlow(false)
    val isGeneratingDevotional: StateFlow<Boolean> = _isGeneratingDevotional.asStateFlow()

    private val _devotionalError = MutableStateFlow<String?>(null)
    val devotionalError: StateFlow<String?> = _devotionalError.asStateFlow()

    fun generateTodayDevotional() {
        _isGeneratingDevotional.value = true
        _devotionalError.value = null
        viewModelScope.launch {
            val apiKey = BuildConfig.GEMINI_API_KEY
            val result = repository.generateDailyDevotional(apiKey)
            _isGeneratingDevotional.value = false
            if (result.isFailure) {
                _devotionalError.value = result.exceptionOrNull()?.message ?: "Failed to generate devotional"
            }
        }
    }
}

class GaroBibleViewModelFactory(
    private val application: Application,
    private val repository: GaroBibleRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GaroBibleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GaroBibleViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
