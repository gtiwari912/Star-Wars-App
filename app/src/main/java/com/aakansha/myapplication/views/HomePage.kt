package com.aakansha.myapplication.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.aakansha.myapplication.R
import com.aakansha.myapplication.repo.model.Character
import com.aakansha.myapplication.ui.theme.StarWarsYellow
import com.aakansha.myapplication.ui.theme.SubTitleTextColor
import com.aakansha.myapplication.ui.theme.TitleTextColor
import com.aakansha.myapplication.utils.AppPreferences
import com.aakansha.myapplication.utils.PrefUtils
import com.aakansha.myapplication.viewmodel.StarWarsViewModel

@Preview
@Composable
fun HomePage() {

    val starWarViewModel =viewModel(modelClass = StarWarsViewModel::class.java)
    val characterList = starWarViewModel.charactersList.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit ){
        starWarViewModel.getCharacters(context = context)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Star Wars Logo
        Image(
            painter= painterResource(id = R.drawable.starwarslogo) ,
            contentDescription = "Star wars logo"
        )

        //search bar
        val searchText = remember{ mutableStateOf("") }
        OutlinedTextField(
            value = searchText.value,
            onValueChange = { searchText.value = it },
            shape = RoundedCornerShape(20.dp),
            trailingIcon = {
                Row() {
                    Icon(painter = painterResource(id = R.drawable.filter), contentDescription = "", tint = StarWarsYellow, modifier = Modifier
                        .padding(end = 20.dp)
                        .size(30.dp))
                    Icon(imageVector = Icons.Default.Search, contentDescription = "", tint = StarWarsYellow, modifier = Modifier
                        .padding(end = 20.dp)
                        .size(30.dp))
                }

                           },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
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
                .padding(horizontal = 40.dp)
                .padding(bottom = 50.dp),
//            columns = GridCells.Adaptive(minSize = 150.dp)
        ) {
            if(!characterList.value.isNullOrEmpty()){
                var index = 0
                characterList.value?.forEachIndexed { index, character ->
                    if(index%2==0){
                        item {
                            Row() {
                                Log.d("tag912", "on index: $index")
                                CharacterCard(characterList?.value?.get(index)!!, index = index)
                                if(index+1 < characterList?.value?.size!!){
                                    CharacterCard(characterList?.value?.get(index+1)!!, index = index+1)
                                }
                            }
                        }
                    }
                }
            }

            item {
                if(PrefUtils.getBooleanPref(context = context,AppPreferences.REACHED_LAST_PAGE)){
                    Text(
                        text = "Your Reached End of Page ;)",
                        fontSize = 12.sp,
                        color = SubTitleTextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth()
                    )
                }
                else{
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, StarWarsYellow, RoundedCornerShape(20.dp))
                            .padding(horizontal = 50.dp),

                        onClick = {
                            starWarViewModel.getCharacters(context = context)
                        }) {

                        Text(
                            text = "Load More",
                            fontSize = 18.sp,
                            color = SubTitleTextColor
                        )


                    }
                }

            }




        }


    }

}


@Composable
fun CharacterCard(
    character: Character,
    index:Int
){
//   val  character: Character = Character(name = "Lions King", gender = "male", created = "2014-12-09T13:50:51.644000Z", edited = "2014-12-20T21:17:56.891000Z")
    Column(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
//            .background(Color.Black)
            .background(darkGradientBrudshes[index % darkGradientBrudshes.size])
            .size(150.dp)
            .padding(10.dp)
    ){
        Text(
            text = character.name ?: "",
            fontSize = 20.sp,
            color = TitleTextColor,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
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
        }
        

        //gender
        Text(
            text = "Gender: ${character.name ?: ""}",
            fontSize = 12.sp,
            color = SubTitleTextColor,
        )




        //created at
        Text(
            text = "Created At: ${character.created}",
            fontSize = 12.sp,
            color = SubTitleTextColor,
        )

        //updated at
        Text(
            text = "Updated At: ${character.edited?:""}",
            fontSize = 12.sp,
            color = SubTitleTextColor,
        )


    }
}




val darkGradientBrudshes = arrayListOf(

    // Deep Ocean: Blue to Purple
    Brush.verticalGradient(
        colors = listOf(Color(0xFF34495E), Color(0xFF4A148C)),
        tileMode = TileMode.Clamp
    ),

    // Midnight Sky: Navy to Black
    Brush.verticalGradient(
        colors = listOf(Color(0xFF2C3E50), Color(0xFF000000)),
        tileMode = TileMode.Clamp
    ),

    // Dark Forest: Green to Black
    Brush.verticalGradient(
        colors = listOf(Color(0xFF22543D), Color(0xFF000000)),
        tileMode = TileMode.Clamp
    ),

    // Dusky Moon: Dark Gray to Black
    Brush.verticalGradient(
        colors = listOf(Color(0xFF484848), Color(0xFF000000)),
        tileMode = TileMode.Clamp
    ),

    // Blood Moon: Deep Red to Black
    Brush.verticalGradient(
        colors = listOf(Color(0xFF990000), Color(0xFF000000)),
        tileMode = TileMode.Clamp
    ),

    // Cosmic Dusk: Purple to Dark Blue
    Brush.verticalGradient(
        colors = listOf(Color(0xFF4A148C), Color(0xFF34495E)),
        tileMode = TileMode.Clamp
    ),

    // Nebula Dream: Dark Purple to Violet
    Brush.verticalGradient(
        colors = listOf(Color(0xFF4A148C), Color(0xFF5E5CA7)),
        tileMode = TileMode.Clamp
    ),

    // Twilight Fog: Dark Gray to Purple
    Brush.verticalGradient(
        colors = listOf(Color(0xFF454545), Color(0xFF4A148C)),
        tileMode = TileMode.Clamp
    ),

    // Galaxy Glow: Blue to Purple to Pink
    Brush.verticalGradient(
        colors = listOf(Color(0xFF34495E), Color(0xFF4A148C), Color(0xFFC51162)),
        tileMode = TileMode.Clamp
    ),

    // Aurora Night: Blue to Green to Purple
    Brush.verticalGradient(
        colors = listOf(Color(0xFF3498DB), Color(0xFF2ECC71), Color(0xFF9B59B6)),
        tileMode = TileMode.Clamp
    )
)


