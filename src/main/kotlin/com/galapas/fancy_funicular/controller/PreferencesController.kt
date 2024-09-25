package com.galapas.fancy_funicular.controller

import com.galapas.fancy_funicular.entity.Preferences
import com.galapas.fancy_funicular.service.PreferencesService
import jakarta.validation.constraints.Email
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = arrayOf("*"))
@RequestMapping("/v1/api")
class PreferencesController {
    lateinit var prefsService: PreferencesService

    @Autowired
    fun initialize(prefsService: PreferencesService) {
        this.prefsService = prefsService
    }

    @GetMapping("/preferences/")
    fun getPreferences(): ResponseEntity<*> {
        var resultMsg = prefsService.getAllPreferences()

        return when (resultMsg.code) {
            HttpStatus.OK -> ResponseEntity<Any>(resultMsg, resultMsg.code as HttpStatus)
            HttpStatus.BAD_REQUEST -> ResponseEntity<Any>(resultMsg, resultMsg.code as HttpStatus)
            else -> ResponseEntity<Any>(resultMsg, resultMsg.code as HttpStatus)
        }
    }

    @GetMapping("/preferences/{emailAddress}")
    fun getPreferences(@PathVariable @Email emailAddress: String): ResponseEntity<*> {
        var resultMsg = prefsService.getPreferencesByEmail(emailAddress)

        return when (resultMsg.code) {
            HttpStatus.OK -> ResponseEntity<Any>(resultMsg, resultMsg.code as HttpStatus)
            HttpStatus.BAD_REQUEST -> ResponseEntity<Any>(resultMsg, resultMsg.code as HttpStatus)
            else -> ResponseEntity<Any>(resultMsg, resultMsg.code as HttpStatus)
        }
    }

    @PostMapping("/preferences/")
    fun savePreferences(@RequestBody preferences: Preferences): ResponseEntity<*> {
        var resultMsg = prefsService.savePreferences(preferences)

        return when (resultMsg.code) {
            HttpStatus.OK -> ResponseEntity<Any>(resultMsg, resultMsg.code as HttpStatus)
            HttpStatus.BAD_REQUEST -> ResponseEntity<Any>(resultMsg, resultMsg.code as HttpStatus)
            else -> ResponseEntity<Any>(resultMsg, resultMsg.code as HttpStatus)
        }
    }
}
