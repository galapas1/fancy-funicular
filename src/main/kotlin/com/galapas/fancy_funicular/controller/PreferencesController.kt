package com.galapas.fancy_funicular.controller

import com.galapas.fancy_funicular.dto.Message
import com.galapas.fancy_funicular.entity.Preferences
import jakarta.validation.constraints.Email
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/api")
class PreferencesController {
    lateinit var prefsService: PreferencesService

    @Autowired
    fun initialize(prefsService: PreferencesService) {
        this.prefsService = prefsService
    }

    @GetMapping("/preferences/")
    fun getPreferences(): Message {
        var resp = prefsService.getAllPreferences()

        return resp
    }

    @GetMapping("/preferences/{emailAddress}")
    fun getPreferences(@PathVariable @Email emailAddress: String): Message {
        var resp = prefsService.getPreferencesByEmail(emailAddress)

        return resp
    }

    @PostMapping("/preferences/")
    fun savePreferences(@RequestBody preferences: Preferences): Message {
        var resp = prefsService.savePreferences(preferences)

        return resp
    }
}
