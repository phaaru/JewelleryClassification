package com.example.jewelleryclassification.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Defines methods for using the JWImage class with Room.
 */
@Dao
interface JWDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(jwImage: JWImage)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(jwImage: JWImage)

//    Get List, getting used for classifying unclassified images.
    @Query("SELECT * FROM image_table WHERE type = :type ORDER BY imageId DESC")
    fun getListOfType(type: String): List<JWImage>

//    Query for Getting Live Data of All Images of a Type.
    @Query("SELECT * FROM image_table WHERE type = :type ORDER BY imageId DESC")
    fun getAllImagesOfType(type: String): LiveData<List<JWImage>>

//    Query for getting 1 of each type
    @Query("SELECT * FROM image_table GROUP BY type")
    fun getAllTypes(): LiveData<List<JWImage>>

//    Not Being Used
    @Query("SELECT * FROM image_table ORDER BY imageId DESC")
    fun getAllImages(): LiveData<List<JWImage>>

    @Query("SELECT * FROM image_table ORDER BY imageId DESC LIMIT 1")
    fun getLastImage(): JWImage?

    @Query("DELETE FROM image_table")
    fun clear()

}
