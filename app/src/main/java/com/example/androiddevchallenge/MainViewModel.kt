package com.example.androiddevchallenge

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.androiddevchallenge.ui.element.EggSize
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    var currentSecond = MutableLiveData<Long>(0L)
    var currentEgg = MutableLiveData(EggSize.SMALL)
    var timerState = MutableLiveData(TimerState.STOPPED)

    var currentSecondDisplayString = currentSecond.map {
        val minute = TimeUnit.SECONDS.toMinutes(it)
        String.format(
            "%02d : %02d",
            minute,
            it - TimeUnit.MINUTES.toSeconds(minute)
        );
    }

    private var countDownTimer: CountDownTimer? = null

    fun startTimer(initEgg: EggSize) {
        currentSecond.value = initEgg.time
        currentEgg.value = initEgg
        timerState.value = TimerState.STARTED
        countDownTimer?.cancel()
        countDownTimer = createCountTimer(initEgg.time).apply { start() }
    }

    fun pauseTimer() {
        countDownTimer?.cancel()
        timerState.value = TimerState.PAUSED
    }

    fun resumeTimer() {
        countDownTimer?.cancel()
        countDownTimer = createCountTimer(currentSecond.value ?: 0).apply {
            start()
        }
        timerState.value = TimerState.STARTED
    }

    fun stopTimer() {
        countDownTimer?.cancel()
        timerState.value = TimerState.STOPPED
    }

    private fun createCountTimer(initTime: Long): CountDownTimer {
        return object : CountDownTimer(initTime * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                currentSecond.value = millisUntilFinished / 1000
            }

            override fun onFinish() {
                timerState.value = TimerState.STOPPED
            }
        }
    }
}

enum class TimerState {
    STOPPED, STARTED, PAUSED
}