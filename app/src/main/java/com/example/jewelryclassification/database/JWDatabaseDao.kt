package com.example.jewelryclassification.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Defines methods for using the JWImage class with Room.
 */
@Dao
interface JWDatabaseDao {

    @Insert
    fun insert(jwImage: JWImage)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param night new value to write
     */
    @Update
    fun update(jwImage: JWImage)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from image_table WHERE imageId = :key")
    fun get(key: Long): JWImage?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM image_table")
    fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM image_table ORDER BY imageId DESC")
    fun getAllImages(): LiveData<List<JWImage>>

    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM image_table ORDER BY imageId DESC LIMIT 1")
    fun getLastImage(): JWImage?

}
