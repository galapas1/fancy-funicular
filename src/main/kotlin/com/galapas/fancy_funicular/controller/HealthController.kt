package com.galapas.fancy_funicular.controller

import com.galapas.fancy_funicular.service.HealthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = arrayOf("*"))
@RequestMapping("/v1/api")
class HealthController {

    @Value("\${spring.datasource.url}") lateinit var dataSourceUrl: String

    lateinit var healthService: HealthService

    @Autowired
    fun initialize(healthService: HealthService) {
        this.healthService = healthService
    }

    @GetMapping("/health")
    fun getHealthStatus(): ResponseEntity<*> {
        var resultMsg = healthService.checkHealth(dataSourceUrl)

        return when (resultMsg.code) {
            HttpStatus.OK -> ResponseEntity<Any>(resultMsg, resultMsg.code as HttpStatus)
            HttpStatus.BAD_REQUEST -> ResponseEntity<Any>(resultMsg, resultMsg.code as HttpStatus)
            else -> ResponseEntity<Any>(resultMsg, resultMsg.code as HttpStatus)
        }
    }
}
