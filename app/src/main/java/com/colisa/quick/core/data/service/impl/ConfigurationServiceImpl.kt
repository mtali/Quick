package com.colisa.quick.core.data.service.impl

import com.colisa.quick.BuildConfig
import com.colisa.quick.core.data.service.ConfigurationService
import com.colisa.quick.core.data.service.LogService
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import javax.inject.Inject
import com.colisa.quick.R.xml as AppConfig

class ConfigurationServiceImpl @Inject constructor(
    private val logService: LogService
) : ConfigurationService {

    private val remoteConfig get() = Firebase.remoteConfig

    init {
        if (BuildConfig.DEBUG) {
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 0
            }
            remoteConfig.setConfigSettingsAsync(configSettings)
        }
        remoteConfig.setDefaultsAsync(AppConfig.remote_config_defaults)
    }

    override fun fetchConfiguration() {
        val fetchConfigTrace = Firebase.performance.newTrace(FETCH_CONFIG_TRACE)
        fetchConfigTrace.start()
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener {
                fetchConfigTrace.stop()
                if (!it.isSuccessful) logService.logNonFatalCrash(it.exception)
            }
    }

    override fun getShowTaskEditButtonConfig(): Boolean {
        return remoteConfig[SHOW_TASK_EDIT_BUTTON_KEY].asBoolean()
    }

    companion object {
        private const val SHOW_TASK_EDIT_BUTTON_KEY = "show_task_edit_button"
        private const val FETCH_CONFIG_TRACE = "fetchConfig"
    }
}