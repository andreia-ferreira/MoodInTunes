package net.penguin.data.local

import javax.inject.Inject

class SearchMemoryDataSource @Inject constructor(): KeyValueMemoryDataSource<String, CachedSearchData>()