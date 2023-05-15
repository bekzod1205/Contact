package com.example.contact.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var name:String,
    var phone_number:String
)