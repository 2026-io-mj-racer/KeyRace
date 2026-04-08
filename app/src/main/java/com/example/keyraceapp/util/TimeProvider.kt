package com.example.keyraceapp.util

import android.os.SystemClock

interface TimeProvider {
    fun now() = SystemClock.elapsedRealtime()
}

class AndroidTimeProvider: TimeProvider {

}