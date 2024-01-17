package com.aakansha.myapplication.repo.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class], version = 1)
abstract class StarWarCharactersDb : RoomDatabase() {
    abstract fun starwarcharacterdao(): StarWarCharacterDao
}

object StarWarCharacterProvider {
    private var db : StarWarCharactersDb? = null
    fun provideCharacterDb(context: Context): StarWarCharactersDb {
        if(db==null){
            db = Room.databaseBuilder(
                context,
                StarWarCharactersDb::class.java, "starwarCharacterDb"
            ).build()
        }
        return db!!
    }
}