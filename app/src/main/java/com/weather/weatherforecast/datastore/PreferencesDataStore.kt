package com.weather.weatherforecast.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    suspend fun setEnableAppCount(count: Int) {
        context.dataStore.edit { settings ->
            settings[ENABLE_APP_COUNT] = count
        }
    }

    fun getEnableAppCount(): Flow<Int> {
        return context.dataStore.data.map { settings ->
            settings[ENABLE_APP_COUNT] ?: 0
        }
    }

    companion object {
        private val ENABLE_APP_COUNT = intPreferencesKey("enable_app_count")

    }
}