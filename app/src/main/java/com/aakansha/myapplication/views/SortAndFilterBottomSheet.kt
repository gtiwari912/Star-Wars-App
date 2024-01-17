package com.aakansha.myapplication.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aakansha.myapplication.ui.theme.StarWarsYellow
import com.aakansha.myapplication.utils.Utils
import com.aakansha.myapplication.viewmodel.StarWarsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SortAndFilterBottomSheet(
    openBottomSheet: MutableState<Boolean>,
) {
    val starWarViewModel = viewModel(modelClass = StarWarsViewModel::class.java)
    val filterByOption = remember {
        mutableStateOf("")
    }
    val sortBy = remember { mutableStateOf("") }
    val order = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val context = LocalContext.current
    var showPage by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
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
            .fillMaxHeight(0.5f)
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
                text = "Filter",
                fontSize = 30.sp,
                color = StarWarsYellow,
                fontFamily = FontFamily.Monospace
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Gender: ",
                    fontSize = 16.sp,
                    color = StarWarsYellow
                )

                RadioButton(
                    selected = filterByOption.value == "Male",
                    onClick = { filterByOption.value = "Male" }
                )
                Text(
                    "Male",
                    color = StarWarsYellow
                )

                RadioButton(
                    selected = filterByOption.value == "Female",
                    onClick = { filterByOption.value = "Female" }
                )
                Text(
                    "Female",
                    color = StarWarsYellow
                )
            }
            Text(
                text = "Sort By",
                fontSize = 30.sp,
                color = StarWarsYellow,
                fontFamily = FontFamily.Monospace
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = sortBy.value == "Height",
                    onClick = { sortBy.value = "Height" }
                )
                Text(
                    "Height",
                    color = StarWarsYellow
                )
                RadioButton(
                    selected = sortBy.value == "Weight",
                    onClick = { sortBy.value = "Weight" }
                )
                Text(
                    "Created At",
                    color = StarWarsYellow
                )
                RadioButton(
                    selected = sortBy.value == "Edited At",
                    onClick = { sortBy.value = "Edited At" }
                )
                Text(
                    "Edited At",
                    color = StarWarsYellow
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "Order: ", color = StarWarsYellow)

                RadioButton(
                    selected = order.value == "Ascending",
                    onClick = { order.value = "Ascending" }
                )
                Text(
                    "Ascending",
                    color = StarWarsYellow
                )
                RadioButton(
                    selected = order.value == "Descending",
                    onClick = { order.value = "Descending" }
                )
                Text(
                    "Descending",
                    color = StarWarsYellow
                )
            }


            Row(
                modifier = Modifier
                    .padding(top=30.dp)
                    .align(Alignment.End)
            ) {

                Button(
                    onClick = {
                            starWarViewModel.reset(context = context)
                              openBottomSheet.value = openBottomSheet.value.not()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = StarWarsYellow
                    ),
                    modifier = Modifier
                        .padding(end = 20.dp)
                ) {
                    Text(text = "Clear", color= Color.Black)
                }
                Button(
                    onClick = {
                            starWarViewModel.applyFilter(context = context, filter = filterByOption.value, sort = sortBy.value, true)
                        openBottomSheet.value = openBottomSheet.value.not()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = StarWarsYellow
                    )
                ) {
                    Text(text = "Apply", color= Color.Black)
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


