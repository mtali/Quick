package com.colisa.quick.core.data.service

interface ConfigurationService {
    fun fetchConfiguration()
    fun getShowTaskEditButtonConfig(): Boolean
}