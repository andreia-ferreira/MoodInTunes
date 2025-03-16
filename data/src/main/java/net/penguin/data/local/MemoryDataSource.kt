package net.penguin.data.local

abstract class KeyValueMemoryDataSource<K, V> {
    private var data: MutableMap<K, V> = mutableMapOf()

    fun set(key: K, value: V) {
        data[key] = value
    }

    fun get(key: K): V? = data[key]

    fun clear() {
        data.clear()
    }
}