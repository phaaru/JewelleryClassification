package com.jamlabsai.srj.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_table")
data class JWImage(
    @PrimaryKey(autoGenerate = true)
    val imageId: Long = 0L,

    @ColumnInfo(name = "uri")
    var path: String = "",

    @ColumnInfo(name = "j_uri")
    var jPath: String = "",

    @ColumnInfo(name = "type")
    var type: String = "unclassified"
)