package com.aakansha.myapplication.views

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aakansha.myapplication.repo.model.Films
import com.aakansha.myapplication.repo.model.StarWarCharacter
import com.aakansha.myapplication.ui.theme.StarWarsYellow
import com.aakansha.myapplication.ui.theme.SubTitleTextColor
import com.aakansha.myapplication.ui.theme.TitleTextColor
import com.aakansha.myapplication.utils.Utils
import com.aakansha.myapplication.viewmodel.StarWarsViewModel
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CharacterDetailsBottomSheet(
    openBottomSheet: MutableState<Boolean>,
) {
    val starWarViewModel = viewModel(modelClass = StarWarsViewModel::class.java)
    val character = starWarViewModel.characterDetails.observeAsState()
    val films = starWarViewModel.starWarFilms.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val context = LocalContext.current
    var showPage by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        starWarViewModel.getStarWarFilms(context = context)
        coroutineScope.launch(Dispatchers.IO) {
            delay(200L)
            showPage = true
        }
    }
    ModalBottomSheet(
        onDismissRequest = {
            coroutineScope.launch(Dispatchers.IO) { openBottomSheet.value = false }
        },
        sheetState = modalSheetState,
        containerColor = Color(0xAE000000),
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = Color.White,
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
    ) {
        val brush = remember {
            mutableStateOf(Utils.darkGradientBrudshes.random())
        }
        Column(
            modifier = Modifier
                .background(
                    brush = brush.value
                )
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .fillMaxSize()
        )
        {
            Text(
                text = character.value?.name?:"",
                fontSize = 40.sp,
                color = StarWarsYellow,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
//            @SerializedName("name"       ) var name      : String?           = null,
//            @SerializedName("height"     ) var height    : String?           = null,
//            @SerializedName("mass"       ) var mass      : String?           = null,
//            @SerializedName("hair_color" ) var hairColor : String?           = null,
//            @SerializedName("skin_color" ) var skinColor : String?           = null,
//            @SerializedName("eye_color"  ) var eyeColor  : String?           = null,
//            @SerializedName("birth_year" ) var birthYear : String?           = null,
//            @SerializedName("gender"     ) var gender    : String?           = null,
//            @SerializedName("homeworld"  ) var homeworld : String?           = null,
//            @SerializedName("films"      ) var films     : ArrayList<String> = arrayListOf(),


            Text(
                text = "Height: ${character.value?.height?:""}",
                fontSize = 20.sp,
                color = StarWarsYellow,
            )
            Text(
                text = "Weight: ${character.value?.mass?:""}",
                fontSize = 20.sp,
                color = StarWarsYellow,
            )
            Text(
                text = "Hair Color: ${character.value?.hairColor?:""}",
                fontSize = 20.sp,
                color = StarWarsYellow,
            )
            Text(
                text = "Skin Color: ${character.value?.skinColor?:""}",
                fontSize = 20.sp,
                color = StarWarsYellow,
            )
            Text(
                text = "Eye Color: ${character.value?.eyeColor?:""}",
                fontSize = 20.sp,
                color = StarWarsYellow,
            )
            Text(
                text = "Gender: ${character.value?.gender?:""}",
                fontSize = 20.sp,
                color = StarWarsYellow,
            )

            Text(
                text = "Films",
                fontSize = 30.sp,
                color = StarWarsYellow,
                modifier = Modifier.padding(top = 20.dp)
            )

            var index = 0
            val filmsOfThisCharacter = ArrayList<Films>()
            character.value?.films?.forEach { film->

                films.value?.forEach {
                    if(it.url== film){
                        filmsOfThisCharacter.add(it)
                    }
                }
            }
            filmsOfThisCharacter.forEachIndexed { index, film ->
                if (index % 2 == 0) {
                        Row(
                            Modifier.fillMaxWidth()
                        ) {
                            FilmCard(film = film, index = index)
                            if (index + 1 < character.value?.films?.size!!) {
                                FilmCard(film = film, index = index)
                            }
                        }
                }
            }


        }
    }


    BackHandler() {
        coroutineScope.launch(Dispatchers.IO) {
            modalSheetState.hide()
            openBottomSheet.value = false
        }
    }
}


@Composable
fun FilmCard(
    film: Films,
    index: Int,
) {
//   val  character: Character = Character(name = "Lions King", gender = "male", created = "2014-12-09T13:50:51.644000Z", edited = "2014-12-20T21:17:56.891000Z")
    val starWarViewModel = viewModel(modelClass = StarWarsViewModel::class.java)
        Column(
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
//            .background(Color.Black)
                .background(Utils.darkGradientBrudshes[index % Utils.darkGradientBrudshes.size])
                .size(150.dp)
                .padding(10.dp)
        ) {
            Text(
                text = film.title ?: "",
                fontSize = 20.sp,
                color = TitleTextColor,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // weight
            Text(
                text = "Director: ${film.director ?: ""}",
                fontSize = 12.sp,
                color = SubTitleTextColor,
            )


            //height
            Text(
                text = "Producer: ${film.producer ?: ""}",
                fontSize = 12.sp,
                color = SubTitleTextColor,
            )


            //gender
            Text(
                text = "Release Date: ${film.releaseDate ?: ""}",
                fontSize = 12.sp,
                color = SubTitleTextColor,
            )


            //created at
            Text(
                text = "Created: ${Utils.convertDateTimeStringToDateString(film.created ?: "")}",
                fontSize = 12.sp,
                color = SubTitleTextColor,
            )

            //updated at
            Text(
                text = "Edited: ${Utils.convertDateTimeStringToDateString(film.edited ?: "") ?: ""}",
                fontSize = 12.sp,
                color = SubTitleTextColor,
            )


        }
}


