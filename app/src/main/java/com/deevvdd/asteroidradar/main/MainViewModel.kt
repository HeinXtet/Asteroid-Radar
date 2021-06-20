package com.deevvdd.asteroidradar.main

import androidx.lifecycle.*
import com.deevvdd.asteroidradar.api.getSevenDayTodayOnwards
import com.deevvdd.asteroidradar.api.getToday
import com.deevvdd.asteroidradar.domain.model.Asteroid
import com.deevvdd.asteroidradar.domain.repository.AsteroidRepository
import com.deevvdd.asteroidradar.utils.AsteroidFilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: AsteroidRepository) : ViewModel() {

    val pictureOfDayUrl = Transformations.map(repository.getPictureOfDay()) {
        if (it?.mediaType == "image") it.url else ""
    }

    val pictureOfDayDescription = Transformations.map(repository.getPictureOfDay()) {
        it?.title ?: "PictureOfDay Descriptions"
    }

    private var currentFilterType = AsteroidFilterType.WEEKLY

    private val _loading = MutableLiveData<Boolean>()


    private val loadData = MutableLiveData<Boolean>()

    val asteroidList = Transformations.switchMap(loadData) {
        filterAsteroid()
    }

    val loading = Transformations.map(_loading) {
        _loading
    }

    private val _emptyAsteroids = Transformations.map(asteroidList) {
        it.orEmpty().isEmpty()
    }

    val emptyAsteroids: LiveData<Boolean>
        get() = _emptyAsteroids


    init {
        loadData.value = true
        refreshPictureOfDay()
        refreshAsteroidData()
    }

    private fun refreshPictureOfDay() {
        viewModelScope.launch {
            _loading.value = true
            repository.fetchImageOfTheDay().collect({
                _loading.value = false
            }, {
                _loading.value = false
            })
        }
    }

    private fun refreshAsteroidData() {
        viewModelScope.launch {
            _loading.value = true
            repository.fetchAsteroidData(
                getToday(),
                getSevenDayTodayOnwards(),
            ).collect({
                _loading.value = false
            }, {
                _loading.value = false
            })
        }
    }


    fun setFilterType(requestFilterType: AsteroidFilterType) {
        currentFilterType = requestFilterType
        loadData.value = true
    }


    private fun filterAsteroid(): LiveData<List<Asteroid>> {
        return when (currentFilterType) {
            AsteroidFilterType.TODAY -> {
                repository.getAsteroidWithClosetDate(getToday(), getToday())
            }
            AsteroidFilterType.ALL -> {
                repository.getAllSavedAsteroid()
            }
            AsteroidFilterType.WEEKLY -> {
                repository.getAsteroidWithClosetDate(getToday(), getSevenDayTodayOnwards())
            }
        }
    }
}