package com.galapas.fancy_funicular.controller

import org.springframework.beans.factory.annotation.Autowired

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

import org.springframework.beans.factory.annotation.Value

import com.fasterxml.jackson.databind.ObjectMapper
import com.galapas.fancy_funicular.entity.Preferences
import com.galapas.fancy_funicular.repository.PreferencesRepository

@RestController
@RequestMapping("/v1/api")
class PreferencesController {
    lateinit var prefsRepository: PreferencesRepository

    @Autowired
    fun initialize(prefsRepository: PreferencesRepository) {
        this.prefsRepository = prefsRepository
    }

    @GetMapping("/preferences/{emailAddr}")
    fun getPreferences(@PathVariable emailAddr: String): String {
        // TODO: validate email address
        var existingPrefs = prefsRepository.findByEmailAddress(emailAddr).orElse(null)
        if (existingPrefs == null) {
            val data = mapOf(
                "status" to "failed",
                "message" to "No topping preferences found for ${emailAddr}."
            )

            return ObjectMapper().writeValueAsString(data) 
        }

        val data = mapOf(
            "status" to "success",
            "message" to "Preferences for ${existingPrefs.emailAddress} are ${existingPrefs.tags.joinToString(",")}"
        )

        return ObjectMapper().writeValueAsString(data) 
    }

    @PostMapping("/preferences/")
    fun savePreferences(@RequestBody preferences: Preferences): String {
        prefsRepository.save(preferences) 
        val data = mapOf(
            "status" to "success",
            "message" to "Topping preferences for ${preferences.emailAddress} have been saved"
        )

        return ObjectMapper().writeValueAsString(data) 
    }
}
