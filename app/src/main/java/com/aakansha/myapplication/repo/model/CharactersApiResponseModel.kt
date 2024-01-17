package com.aakansha.myapplication.repo.model

import com.google.gson.annotations.SerializedName

data class CharactersApiResponseModel (

    @SerializedName("count"    ) var count    : Int?               = null,
    @SerializedName("next"     ) var next     : String?            = null,
    @SerializedName("previous" ) var previous : String?            = null,
    @SerializedName("results"  ) var results  : ArrayList<Character> = arrayListOf()

)

data class Character (

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
    @SerializedName("url"        ) var url       : String?           = null

)