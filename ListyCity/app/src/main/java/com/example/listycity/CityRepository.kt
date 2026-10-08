package com.example.listycity

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore

class CityRepository {

    private val db = Firebase.firestore
    private val citiesRef = db.collection("cities")
    private val _cities = mutableStateListOf(
        City("Edmonton", "AB"),
        City("Vancouver", "BC"),
        City("Toronto", "ON")
    )

    val cities: List<City>
        get() = _cities

    fun addCity(cityName: String, provinceName: String) {
        citiesRef.document().set(
            City(
                cityName,
                provinceName
            )
        )
    }

    fun updateCity(oldCity: City, updatedCity: City) {

        citiesRef.document(oldCity.uniqueID).set(updatedCity)
    }

    fun onDeleteCity(city: City) {
        citiesRef.document(city.uniqueID).delete()
    }

    init {
        citiesRef.addSnapshotListener { snapshots, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            _cities.clear()

            snapshots?.documents?.forEach { document ->
                val city = document.toObject(City::class.java)
                if (city != null){
                    _cities.add(city)
                }
            }
        }
    }
}
