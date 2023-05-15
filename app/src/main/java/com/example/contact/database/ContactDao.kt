package com.example.contact.database

import androidx.room.*

@Dao
interface ContactDao {
    @Query("select * from contacts")
    fun getAllUsers(): MutableList<Contact>

    @Insert
    fun addContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Update
    fun updateContact(contact: Contact)
}