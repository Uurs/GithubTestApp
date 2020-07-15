package bohdan.varchenko.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

internal fun createDatabase(context: Context): AppDatabase =
    Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
        .build()