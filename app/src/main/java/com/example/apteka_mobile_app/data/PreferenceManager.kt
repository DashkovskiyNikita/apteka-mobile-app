package com.example.apteka_mobile_app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.apteka_mobile_app.data.api.UserType
import kotlinx.coroutines.flow.map

class PreferenceManager(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences")

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("accessToken")
        private val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
        private val USER_AUTH_STATE = booleanPreferencesKey("user_auth_state")
        private val USER_TYPE = stringPreferencesKey("user_type")
    }

    val accessTokenFlow = context.dataStore.data.map { it[ACCESS_TOKEN] ?: "" }

    val refreshTokenFlow = context.dataStore.data.map { it[REFRESH_TOKEN] ?: "" }

    val isUserAuthorizedFlow = context.dataStore.data.map { it[USER_AUTH_STATE] ?: false }

    val userTypeFlow = context.dataStore.data.map {
        it[USER_TYPE]?.let { user -> UserType.valueOf(user) } ?: UserType.NONE
    }

    suspend fun setIsUserAuthorized(isAuthorized: Boolean) {
        context.dataStore.edit {
            it[USER_AUTH_STATE] = isAuthorized
        }
    }

    suspend fun setAccessToken(accessToken: String) {
        context.dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun setRefreshToken(refreshToken: String) {
        context.dataStore.edit {
            it[REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun setUserType(userType: UserType) {
        context.dataStore.edit {
            it[USER_TYPE] = userType.name
        }
    }
}