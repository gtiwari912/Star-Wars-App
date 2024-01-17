package com.aakansha.myapplication.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aakansha.myapplication.repo.StarWarApi
import com.aakansha.myapplication.repo.localdb.CharacterEntity
import com.aakansha.myapplication.repo.localdb.StarWarCharacterProvider
import com.aakansha.myapplication.repo.localdb.StarWarCharactersDb
import com.aakansha.myapplication.repo.model.Films
import com.aakansha.myapplication.repo.model.StarWarCharacter
import com.aakansha.myapplication.utils.AppPreferences
import com.aakansha.myapplication.utils.PrefUtils
import com.aakansha.myapplication.utils.Utils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await
import java.security.Provider


class StarWarsViewModel : ViewModel(){
    private val _charactersList = MutableLiveData<ArrayList<StarWarCharacter>>()
    val charactersList: LiveData<ArrayList<StarWarCharacter>>
        get() = _charactersList

    private val _characterDetails = MutableLiveData<StarWarCharacter>()
    val characterDetails: LiveData<StarWarCharacter>
        get() = _characterDetails

    val _starWarFilms = MutableLiveData<ArrayList<Films>>()
    val starWarFilms: LiveData<ArrayList<Films>>
        get() = _starWarFilms

    var filter = ""
    var sort = ""
    var isOrderAscending = true

    fun setCharacterDetails(character: StarWarCharacter){
        _characterDetails.value = character
    }

    fun getStarWarFilms(context: Context){
        if(_starWarFilms.value.isNullOrEmpty()){
            viewModelScope.launch(Dispatchers.IO) {
                val films = StarWarApi.starWarApiInstance.getStarWarsFilms().await().results
                withContext(Dispatchers.Main){
                    _starWarFilms.postValue(films)
                }
            }
        }
    }

    fun getCharactersFromApi(context: Context){
        Log.d("tag912", "IN net wala")
        if(charactersList.value.isNullOrEmpty()){
            PrefUtils.setIntPref(context = context, AppPreferences.PAGE_NUMBER, 0)
            PrefUtils.setBooleanPref(context = context, AppPreferences.REACHED_LAST_PAGE, false)
        }
        if(PrefUtils.getBooleanPref(context = context, AppPreferences.REACHED_LAST_PAGE) == true){
            return
        }
        var pageNumber = 0;
        if(!PrefUtils.get(context = context).contains(AppPreferences.PAGE_NUMBER)){
            PrefUtils.setIntPref(context = context, key = AppPreferences.PAGE_NUMBER, pageNumber)
        }
        pageNumber = PrefUtils.getIntPref(context = context, AppPreferences.PAGE_NUMBER) +1
        PrefUtils.setIntPref(context = context, key = AppPreferences.PAGE_NUMBER, pageNumber)
        viewModelScope.launch(Dispatchers.IO){
            Log.d("tag912", "Calling for pageno: $pageNumber")
            val starWarCharactersApiResponse = StarWarApi.starWarApiInstance.getCharacters(page = pageNumber).await()
            val characters = starWarCharactersApiResponse.results
            characters.forEach {
                StarWarCharacterProvider
                    .provideCharacterDb(context)
                    .starwarcharacterdao()
                    .insertCharacter(
                        it.toCharacterEntity()
                    )
            }

            if(starWarCharactersApiResponse.next == null){
                Log.d("tag912", "Setting reached last page")
                PrefUtils.setBooleanPref(context = context, AppPreferences.REACHED_LAST_PAGE, true)
            }
            val updatedCharactersList = ArrayList<StarWarCharacter>()
            _charactersList.value?.forEach { updatedCharactersList.add(it) }
            characters.forEach { updatedCharactersList.add(it) }
            withContext(Dispatchers.Main){
                _charactersList.postValue(updatedCharactersList)
            }

        }
    }


    private fun getCharactersFromLocalDb(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("tag912", "in db getter")
            val charactersEntity = StarWarCharacterProvider.provideCharacterDb(context = context)
                    .starwarcharacterdao()
                    .getAll()
            Log.d("tag912", "Size: ${charactersEntity.size}")
            Log.d("tag912", "dbGetter complete")

        }
    }

    fun getCharactersFromLocalDbb(context: Context){
        Log.d("tag912","Level 0")
        val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
            throwable.printStackTrace()
        }
//        viewModelScope.launch(Dispatchers.Default+ coroutineExceptionHandler) {
            Log.d("tag912","Level 1")
//            val starWarCharactersEntity = StarWarCharacterProvider
//                .provideCharacterDb(context)
//                .starwarcharacterdao()
//                .getAll()
//            Log.d("tag912","Level 2")
//            val resCharacterList = ArrayList<StarWarCharacter>()
//            starWarCharactersEntity.forEach{
//                resCharacterList.add(it.toCharacter())
//                Log.d("tag912", "Dao: ${it.name}")
//            }
            Log.d("tag912","Level 3")
//            withContext(Dispatchers.Main){
                Log.d("tag912", "Level 3.5")
//                _charactersList.postValue(resCharacterList)
                Log.d("tag912", "Level 4")
//                _charactersList.value = resCharacterList
//            }
//        }

    }

    fun getCharacters(context: Context){
        if(Utils.isNetworkAvailable(context = context)){
            getCharactersFromApi(context = context)
        }
        else{
            getCharactersFromLocalDb(context = context)
        }
    }

    fun applyFilter(context: Context, filter: String, sort: String, isOrderAscending: Boolean){
        var newList = ArrayList<StarWarCharacter>()
        if(filter.equals("male", ignoreCase = true)){
            _charactersList.value?.forEach {
                if(it.gender.equals("male", ignoreCase = true)){
                    newList.add(it)
                }
            }
        }else if(filter.equals("female", ignoreCase = true)){
            _charactersList.value?.forEach {
                if(it.gender.equals("female", ignoreCase = true)){
                    newList.add(it)
                }
            }
        }
        if(newList.isEmpty()){
            newList.apply {
                addAll(_charactersList.value?:ArrayList())
            }
        }
        if(sort.equals("Height", ignoreCase = true)){
            newList.sortBy { it.height?.toInt()?:0 }
        }
        if(sort.equals("Weight", ignoreCase = true)){
            newList.sortBy { it.mass?.toInt() }
        }
        if(sort.equals("Created At", ignoreCase = true)){
            newList.sortBy { Utils.iso8601ToUnixTimestamp(it.created?:"") }
        }
        if (sort.equals("Edited At", ignoreCase = true)){
            newList.sortBy { Utils.iso8601ToUnixTimestamp(it.edited?:"") }
        }
        _charactersList.postValue(newList)
    }

    fun searchCharacter(query: String) {
        var newList = ArrayList<StarWarCharacter>()
        _charactersList.value?.forEach {
            if(it.name?.contains(query, ignoreCase = true) == true){
                newList.add(it)
            }
        }
        _charactersList.postValue(newList)
    }

    fun reset(context: Context) {
        _charactersList.postValue(ArrayList())
        getCharacters(context = context)
    }


}