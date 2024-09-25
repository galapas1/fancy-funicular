package com.galapas.fancy_funicular.controller

import com.galapas.fancy_funicular.dto.Message
import com.galapas.fancy_funicular.entity.Preferences
import com.galapas.fancy_funicular.repository.PreferencesRepository
import jakarta.validation.constraints.Email
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

    @GetMapping("/preferences/")
    fun getPreferences(): Message {
        var existingPrefs = prefsRepository.findAll()
        if (existingPrefs == null) {
            return Message(
                mapOf("status" to "failed"),
                "No topping preferences found.",
                HttpStatus.NO_CONTENT,
            )
        }

        val preferenceCounts = mutableMapOf<String, Int>()
        for (pref in existingPrefs) {
            for (tag in pref.tags) {
                preferenceCounts[tag] = preferenceCounts.getOrDefault(tag, 0) + 1
            }
        }

        val sb = StringBuilder()
        for (entry in preferenceCounts) {
            if (sb.length > 0) {
                sb.append(", ")
            }
            sb.append("${entry.key}: ${entry.value} user(s)")
        }

        return Message(mapOf("data" to sb.toString()), "All preferences", HttpStatus.OK)
    }

    @GetMapping("/preferences/{emailAddress}")
    fun getPreferences(@PathVariable @Email emailAddress: String): Message {
        var existingPrefs = prefsRepository.findByEmailAddress(emailAddress).orElse(null)
        if (existingPrefs == null) {
            return Message(
                mapOf("status" to "failed"),
                "No topping preferences found for ${emailAddress}.",
                HttpStatus.NO_CONTENT,
            )
        }

        return Message(
            mapOf("data" to existingPrefs.tags.joinToString(",")),
            "Preferences for ${existingPrefs.emailAddress}",
            HttpStatus.OK,
        )
    }

    @PostMapping("/preferences/")
    fun savePreferences(@RequestBody preferences: Preferences): Message {
        try {
            var existingPrefs =
                prefsRepository.findByEmailAddress(preferences.emailAddress).orElse(null)

            if (existingPrefs == null) {
                // remove dups...
                preferences.tags = preferences.tags.distinct()

                // TODO: see if all tags are valid topping
                prefsRepository.save(preferences)

                return Message(
                    mapOf("status" to "success"),
                    "Topping preferences for ${preferences.emailAddress} have been saved",
                    HttpStatus.OK,
                )
            }

            existingPrefs.tags = preferences.tags.distinct()
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
