package com.hashcode.serverapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hashcode.serverapp.database.entities.User

@Dao
interface MessagingDao {

    @Insert
    fun insertUser(user: User): Long

    @Query("select * from user_table")
    fun getAllUsers(): List<User>
}