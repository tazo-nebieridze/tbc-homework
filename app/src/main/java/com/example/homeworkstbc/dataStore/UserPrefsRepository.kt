package com.example.homeworkstbc.dataStore
import UserPrefsSerializer
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.yourapp.datastore.UserPrefs
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val USER_PREFS_FILE = "user_prefs.pb"

val Context.userPrefsDataStore: DataStore<UserPrefs> by dataStore(
    fileName = USER_PREFS_FILE,
    serializer = UserPrefsSerializer
)

class UserPrefsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.userPrefsDataStore

    val userPrefsFlow: Flow<UserPrefs> = dataStore.data.map { it }

    suspend fun saveUserDetails(firstName: String, lastName: String, email: String) {
        dataStore.updateData { prefs ->
            prefs.toBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .build()
        }
    }
}