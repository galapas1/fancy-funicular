package com.galapas.fancy_funicular.controller

import com.galapas.fancy_funicular.dto.Message
import com.galapas.fancy_funicular.entity.Preferences
import com.galapas.fancy_funicular.repository.PreferencesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class PreferencesService {
    lateinit var prefsRepository: PreferencesRepository

    @Autowired
    fun initialize(prefsRepository: PreferencesRepository) {
        this.prefsRepository = prefsRepository
    }

    fun getAllPreferences(): Message {
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

    fun getPreferencesByEmail(emailAddress: String): Message {
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

    fun savePreferences(preferences: Preferences): Message {
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
