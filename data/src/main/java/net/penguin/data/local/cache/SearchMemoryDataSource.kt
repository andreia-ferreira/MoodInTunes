package net.penguin.data.local.cache

import net.penguin.data.model.CachedSearchData
import javax.inject.Inject

class SearchMemoryDataSource @Inject constructor(): KeyValueMemoryDataSource<String, CachedSearchData>()