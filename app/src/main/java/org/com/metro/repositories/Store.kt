package org.com.metro.repositories

interface Store {

    fun getValue(key: String): String
    fun setValue(key: String, value: String)

}