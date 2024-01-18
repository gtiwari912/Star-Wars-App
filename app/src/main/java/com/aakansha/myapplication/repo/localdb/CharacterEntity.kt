package com.aakansha.myapplication.repo.localdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aakansha.myapplication.repo.model.StarWarCharacter

@Entity
data class CharacterEntity(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "height") val height: String?,
    @ColumnInfo(name = "mass") val mass: String?,
    @ColumnInfo(name = "hair_color") val hairColor: String?,
    @ColumnInfo(name = "skin_color") val skinColor: String?,
    @ColumnInfo(name = "eye_color") val eyeColor: String?,
    @ColumnInfo(name = "birth_year") val birthYear: String?,
    @ColumnInfo(name = "gender") val gender: String?="",
    @ColumnInfo(name = "homeworld") val homeworld: String?,
    @ColumnInfo(name = "films") val filmsList: String?,
    @ColumnInfo(name = "species") val speciesList: String?,
    @ColumnInfo(name = "vehicles") val vehiclesList: String?,
    @ColumnInfo(name = "starships") val starshipsList: String?,
    @ColumnInfo(name = "created") val created: String?,
    @ColumnInfo(name = "edited") val edited: String?,
    @ColumnInfo(name = "url") val url: String?
){
    fun toCharacter():StarWarCharacter{
        return StarWarCharacter(
            name = name,
            height = height,
            mass = mass,
            hairColor = hairColor,
            skinColor = skinColor,
            eyeColor = eyeColor,
            birthYear = birthYear,
            gender = gender,
            homeworld = homeworld,
            films = ArrayList<String>().apply {
                addAll(
                    if(!filmsList.isNullOrEmpty()){
                        filmsList!!.split(",")
                    }else{
                        listOf()
                    }
                )
                                              },
            species = ArrayList<String>().apply {
                addAll(
                    if(!speciesList.isNullOrEmpty()){
                        speciesList!!.split(",")
                    }else{
                        listOf()
                    }
                )
            },
            vehicles = ArrayList<String>().apply {
                addAll(
                    if(!vehiclesList.isNullOrEmpty()){
                        vehiclesList!!.split(",")
                    }else{
                        listOf()
                    }
                )
            },
            starships = ArrayList<String>().apply {
                addAll(
                    if(!starshipsList.isNullOrEmpty()){
                        starshipsList!!.split(",")
                    }else{
                        listOf()
                    }
                )
            },
            created = created,
            edited = edited,
            url = url
        )
    }
}

