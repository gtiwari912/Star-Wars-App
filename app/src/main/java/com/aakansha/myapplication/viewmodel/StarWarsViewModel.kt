package com.aakansha.myapplication.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aakansha.myapplication.repo.StarWarApi
import com.aakansha.myapplication.repo.model.Character
import com.aakansha.myapplication.utils.AppPreferences
import com.aakansha.myapplication.utils.PrefUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await


class StarWarsViewModel : ViewModel(){
    private val _charactersList = MutableLiveData<ArrayList<Character>>()
    val charactersList: LiveData<ArrayList<Character>>
        get() = _charactersList

    fun getCharacters(context: Context){
        var pageNumber = -1;
        if(!PrefUtils.get(context = context).contains(AppPreferences.PAGE_NUMBER)){
            PrefUtils.setIntPref(context = context, key = AppPreferences.PAGE_NUMBER, pageNumber)
        }
        pageNumber = PrefUtils.getIntPref(context = context, AppPreferences.PAGE_NUMBER) +1
        PrefUtils.setIntPref(context = context, key = AppPreferences.PAGE_NUMBER, pageNumber)
        viewModelScope.launch(Dispatchers.IO){
            Log.d("tag912", "Calling for pageno: $pageNumber")
            val characters = StarWarApi.starWarApiInstance.getCharacters(page = pageNumber).await().results
            withContext(Dispatchers.Main){
                for(character in characters){
                    Log.d("tag912", "Name: ${character.name}")
                    _charactersList.value?.add(character)
                }
            }

        }

    }
}