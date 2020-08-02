package com.hashcode.serverapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hashcode.serverapp.database.entities.User

@Database(entities = [User::class], version = 1)
abstract class MessagingDatabase : RoomDatabase() {
    abstract fun getDao(): MessagingDao
}

class DatabaseManager {

    companion object {
        private var database: MessagingDatabase? = null

        fun getDatabase(context: Context): MessagingDatabase {
            if (database == null) {
                database = Room.databaseBuilder(context, MessagingDatabase::class.java, "messaging_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database as MessagingDatabase
        }
    }

}