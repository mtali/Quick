package com.colisa.quick.core.data.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable?)
}
