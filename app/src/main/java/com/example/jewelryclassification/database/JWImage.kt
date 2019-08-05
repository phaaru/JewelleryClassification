package com.example.jewelryclassification.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_table")
data class JWImage(
    @PrimaryKey(autoGenerate = true)
    var imageId: Long = 0L,

    @ColumnInfo(name = "uri")
    val path: String = "",

    @ColumnInfo(name = "type")
    var type: String = "unclassified"
)