package com.hashcode.serverapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hashcode.serverapp.database.entities.Message
import com.hashcode.serverapp.database.entities.User

@Dao
interface MessagingDao {

    @Insert
    fun insertUser(user: User): Long

    @Query("select * from user_table")
    fun getAllUsers(): List<User>

    @Insert
    fun insertMessage(message: Message): Long

    @Query("select * from message_table where (senderId = :user1Id and recipientId = :user2Id) or (senderId = :user2Id and recipientId = :user1Id)")
    fun getMessagesBetween(user1Id: Long, user2Id: Long): List<Message>
}