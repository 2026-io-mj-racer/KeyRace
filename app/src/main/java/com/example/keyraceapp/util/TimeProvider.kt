package com.example.keyraceapp.util

import android.os.SystemClock
import java.sql.Time

interface TimeProvider {
    fun now() = SystemClock.elapsedRealtime()
}

class AndroidTimeProvider: TimeProvider {

}