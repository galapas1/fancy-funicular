package com.galapas.fancy_funicular.controller

import com.galapas.fancy_funicular.dto.Message
import com.galapas.fancy_funicular.entity.Preferences
import com.galapas.fancy_funicular.repository.PreferencesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/api")
class PreferencesController {
    lateinit var prefsRepository: PreferencesRepository

    @Autowired
    fun initialize(prefsRepository: PreferencesRepository) {
        this.prefsRepository = prefsRepository
    }

    @GetMapping("/preferences/{emailAddress}")
    fun getPreferences(@PathVariable emailAddress: String): Message {
        var existingPrefs = prefsRepository.findByEmailAddress(emailAddress).orElse(null)
        if (existingPrefs == null) {
            return Message(
                mapOf("status" to "failed"),
                "No topping preferences found for ${emailAddress}.",
                HttpStatus.NO_CONTENT,
            )
        }

        return Message(
            mapOf("status" to "success"),
            "Preferences for ${existingPrefs.emailAddress} are ${existingPrefs.tags.joinToString(",")}",
            HttpStatus.OK,
        )
    }

    @PostMapping("/preferences/")
    fun savePreferences(@RequestBody preferences: Preferences): Message {
        try {
            var existingPrefs =
                prefsRepository.findByEmailAddress(preferences.emailAddress).orElse(null)
            if (existingPrefs == null) {
                prefsRepository.save(preferences)

                return Message(
                    mapOf("status" to "success"),
                    "Topping preferences for ${preferences.emailAddress} have been saved",
                    HttpStatus.OK,
                )
            }

            existingPrefs.tags = preferences.tags
            prefsRepository.save(existingPrefs)

            return Message(
                mapOf("status" to "success"),
                "Topping preferences for ${preferences.emailAddress} have been updated",
                HttpStatus.OK,
            )
        } catch (e: Exception) {
            return Message(mapOf("status" to "failed"), "${e.message}", HttpStatus.BAD_REQUEST)
        }
    }
}
