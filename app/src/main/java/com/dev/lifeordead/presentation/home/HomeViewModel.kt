package com.dev.lifeordead.presentation.home

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.lifeordead.R
import com.dev.lifeordead.presentation.data.Ceil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel : ViewModel() {

    private val _cells = MutableStateFlow<List<Ceil>>(emptyList())
    val cells: StateFlow<List<Ceil>> = _cells
    private val history = mutableListOf<Boolean>()

    fun addCeil() {
        val isAlive = Random.nextBoolean()
        val name = if (isAlive) "Живая" else "Мертвая"
        val description = if (isAlive) "Она жива!" else "Увы, она мертва"
        val newCell = Ceil(isAlive, name, description)
        _cells.value += newCell
        history.add(isAlive)

        if (history.size > 3) {
            history.removeAt(0)
        }
        if (history.size == 3) {
            when {
                history.all { it } -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        val lifeCell = Ceil(
                            isAlive = true,
                            name = "Жизнь",
                            description = "Ку-ку!",
                            imageResId = R.drawable.round_lively
                        )
                        _cells.value += lifeCell
                        history.clear()
                    }, 200)
                }

                history.none { it } -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        val deathCell = Ceil(
                            isAlive = false,
                            name = "Смерть",
                            description = "Пока-пока!",
                            imageResId = R.drawable.round_death
                        )
                        _cells.value += deathCell
                        history.clear()
                    }, 200)
                }
            }
        }
    }
}