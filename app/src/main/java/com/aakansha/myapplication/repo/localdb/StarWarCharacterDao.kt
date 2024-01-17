package com.aakansha.myapplication.repo.localdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface StarWarCharacterDao {

    @Query("SELECT * FROM CharacterEntity")
    fun getAll(): List<CharacterEntity>

    @Upsert
    fun insertCharacter( beerEntitiy: CharacterEntity)

    @Delete
    fun deleteCharacter(user: CharacterEntity)

    @Query("SELECT * FROM characterentity WHERE gender LIKE :gender")
    fun getCharactersByGender(gender: String): List<CharacterEntity>

}

