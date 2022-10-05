package com.colisa.quick.core.data.service.impl

import com.colisa.quick.core.data.service.LogService
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class LogServiceImpl @Inject constructor() : LogService {
    override fun logNonFatalCrash(throwable: Throwable?) {
        if (throwable != null) Firebase.crashlytics.recordException(throwable)
    }
}