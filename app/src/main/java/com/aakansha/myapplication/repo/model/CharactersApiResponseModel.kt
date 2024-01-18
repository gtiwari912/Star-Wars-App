package com.aakansha.myapplication.repo.model

import com.aakansha.myapplication.repo.localdb.CharacterEntity
import com.aakansha.myapplication.utils.Utils
import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder

data class CharactersApiResponseModel (

    @SerializedName("count"    ) var count    : Int?               = null,
    @SerializedName("next"     ) var next     : String?            = null,
    @SerializedName("previous" ) var previous : String?            = null,
    @SerializedName("results"  ) var results  : ArrayList<StarWarCharacter> = arrayListOf()

)

data class StarWarCharacter (

    @SerializedName("name"       ) var name      : String?           = null,
    @SerializedName("height"     ) var height    : String?           = null,
    @SerializedName("mass"       ) var mass      : String?           = null,
    @SerializedName("hair_color" ) var hairColor : String?           = null,
    @SerializedName("skin_color" ) var skinColor : String?           = null,
    @SerializedName("eye_color"  ) var eyeColor  : String?           = null,
    @SerializedName("birth_year" ) var birthYear : String?           = null,
    @SerializedName("gender"     ) var gender    : String?           = null,
    @SerializedName("homeworld"  ) var homeworld : String?           = null,
    @SerializedName("films"      ) var films     : ArrayList<String> = arrayListOf(),
    @SerializedName("species"    ) var species   : ArrayList<String> = arrayListOf(),
    @SerializedName("vehicles"   ) var vehicles  : ArrayList<String> = arrayListOf(),
    @SerializedName("starships"  ) var starships : ArrayList<String> = arrayListOf(),
    @SerializedName("created"    ) var created   : String?           = null,
    @SerializedName("edited"     ) var edited    : String?           = null,
    @SerializedName("url"        ) var url       : String?           = null,
var isVisible:Boolean = true

){
    fun toCharacterEntity():CharacterEntity{
        Utils.uid= Utils.uid+1
        return CharacterEntity(
            uid = url?:"${Utils.uid}",
            name = name,
            height = height,
            mass = mass,
            hairColor = hairColor,
            skinColor = skinColor,
            eyeColor = eyeColor,
            birthYear = birthYear,
            gender = gender,
            homeworld = homeworld,
            filmsList = StringBuilder().apply {
                             films.forEach {
                                 this.append(it+",")
                             }
            }.toString(),
            speciesList = StringBuilder().apply {
                species.forEach {
                    this.append(it+",")
                }
            }.toString(),
            vehiclesList = StringBuilder().apply {
                vehicles.forEach {
                    this.append(it+",")
                }
            }.toString(),
            starshipsList = StringBuilder().apply {
                starships.forEach {
                    this.append(it+",")
                }
            }.toString(),
            created = created,
            edited = edited,
            url = url
        )
    }
}