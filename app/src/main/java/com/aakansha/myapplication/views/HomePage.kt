package com.aakansha.myapplication.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aakansha.myapplication.R
import com.aakansha.myapplication.repo.model.StarWarCharacter
import com.aakansha.myapplication.ui.theme.StarWarsYellow
import com.aakansha.myapplication.ui.theme.SubTitleTextColor
import com.aakansha.myapplication.ui.theme.TitleTextColor
import com.aakansha.myapplication.utils.AppPreferences
import com.aakansha.myapplication.utils.PrefUtils
import com.aakansha.myapplication.utils.Utils
import com.aakansha.myapplication.viewmodel.StarWarsViewModel

@Preview
@Composable
fun HomePage() {

    val starWarViewModel = viewModel(modelClass = StarWarsViewModel::class.java)
    val characterList = starWarViewModel.charactersList.observeAsState()
    val context = LocalContext.current
    val openBottomSheet = remember {
        mutableStateOf(false)
    }
    val openBottomSheetForDetails = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        starWarViewModel.getCharacters(context = context)
    }
    if (openBottomSheet.value) {
        SortAndFilterBottomSheet(openBottomSheet = openBottomSheet)
    }
    if(openBottomSheetForDetails.value){
        CharacterDetailsBottomSheet(openBottomSheet = openBottomSheetForDetails)
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .paint(
                painter = painterResource(id = R.drawable.night_background2),
                contentScale = ContentScale.Crop
            )
            ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Star Wars Logo
        Image(
            painter = painterResource(id = R.drawable.starwarslogo),
            contentDescription = "Star wars logo"
        )

        //search bar
        val searchText = remember { mutableStateOf("") }
        OutlinedTextField(
            value = searchText.value,
            onValueChange = {
                searchText.value = it
                if(it.isNullOrEmpty()){
                    starWarViewModel.reset(context = context)
                }
                            },
            shape = RoundedCornerShape(20.dp),
            trailingIcon = {
                Row() {
                    Icon(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "",
                        tint = StarWarsYellow,
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(30.dp)
                            .clickable {
                                openBottomSheet.value = openBottomSheet.value.not()
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = StarWarsYellow,
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(30.dp)
                            .clickable {
                                starWarViewModel.searchCharacter(
                                    searchText.value
                                )
                            },

                    )
                }

            },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = StarWarsYellow,
                unfocusedIndicatorColor = StarWarsYellow,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            modifier = Modifier
                .padding(horizontal = 40.dp, vertical = 10.dp)
                .fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
        ) {
            if (!characterList.value.isNullOrEmpty()) {
                var index = 0
                characterList.value?.forEachIndexed { index, character ->
                    if (index % 2 == 0) {
                        item {
                            Row() {
                                Log.d("tag912", "on index: $index")
                                CharacterCard(characterList?.value?.get(index)!!, index = index, openBottomSheetForDetails)
                                if (index + 1 < characterList?.value?.size!!) {
                                    CharacterCard(
                                        characterList?.value?.get(index + 1)!!,
                                        index = index + 1,
                                        openBottomSheetForDetails
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                if (PrefUtils.getBooleanPref(context = context, AppPreferences.REACHED_LAST_PAGE)) {
                    Text(
                        text = "Your Reached End of Page ;)",
                        fontSize = 12.sp,
                        color = SubTitleTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth()
                    )
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .border(1.dp, StarWarsYellow, RoundedCornerShape(20.dp))
                                .background(Color.Transparent)
                                .align(Alignment.CenterHorizontally),

                            onClick = {
                                searchText.value= ""
                                if(!Utils.isNetworkAvailable(context)){
                                    Toast.makeText(context, "No Internet Available", Toast.LENGTH_SHORT).show()
                                }
                                starWarViewModel.getCharacters(context = context)
                            })
                        {

                            Text(
                                text = "Load More",
                                fontSize = 12.sp,
                                color = SubTitleTextColor
                            )


                        }
                    }

                }

            }


        }


    }

}


@Composable
fun CharacterCard(
    character: StarWarCharacter,
    index: Int,
    openBottomSheet: MutableState<Boolean>
) {
//   val  character: Character = Character(name = "Lions King", gender = "male", created = "2014-12-09T13:50:51.644000Z", edited = "2014-12-20T21:17:56.891000Z")
    val starWarViewModel = viewModel(modelClass = StarWarsViewModel::class.java)
    if(character.isVisible){
        Column(
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
//            .background(Color.Black)
                .background(Utils.darkGradientBrudshes[index % Utils.darkGradientBrudshes.size])
                .size(150.dp)
                .padding(10.dp)
                .clickable {
                    starWarViewModel.setCharacterDetails(character = character)
                    openBottomSheet.value = openBottomSheet.value.not()
                }
        ) {
            Text(
                text = character.name ?: "",
                fontSize = 20.sp,
                color = TitleTextColor,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // weight
            Text(
                text = "Weight: ${character.mass ?: ""}",
                fontSize = 12.sp,
                color = SubTitleTextColor,
            )


            //height
            Text(
                text = "Height: ${character.height ?: ""}",
                fontSize = 12.sp,
                color = SubTitleTextColor,
            )


            //gender
            Text(
                text = "Gender: ${character.gender ?: ""}",
                fontSize = 12.sp,
                color = SubTitleTextColor,
            )


            //created at
            Text(
                text = "Created: ${Utils.convertDateTimeStringToDateString(character.created ?: "")}",
                fontSize = 12.sp,
                color = SubTitleTextColor,
            )

            //updated at
            Text(
                text = "Edited: ${Utils.convertDateTimeStringToDateString(character.edited ?: "") ?: ""}",
                fontSize = 12.sp,
                color = SubTitleTextColor,
            )


        }
    }
}



