package com.imnstudios.hellojetpackcompose

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


data class Puppy(
    val id: Int,
    val puppyName: String,
    val puppyImage: String,
    val age: String,
    val sex: String,
    val color: String,
    val address: String,
    val ownerName: String,
)

class ViewModel : ViewModel() {

    val puppyList: MutableLiveData<List<Puppy>> = MutableLiveData()

    init {
        getPuppyLists()
    }

    private fun getPuppyLists() {
        getPuppies()
        return puppyList.postValue(getPuppies())
    }

    private fun getPuppies(): List<Puppy> {
        val puppies = mutableListOf<Puppy>()
        val puppyOne = Puppy(
            id = 0,
            puppyName = "Appu",
            puppyImage = "https://images.unsplash.com/photo-1591160690555-5debfba289f0?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1400&q=80",
            age = "6 months old",
            sex="Male",
            color = "Cream",
            address = "Palakkad, Kerala",
            ownerName = "Nitheesh",
        )
        puppies.add(puppyOne)
        return puppies
    }

}
