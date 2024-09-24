package com.galapas.fancy_funicular.controller

import com.galapas.fancy_funicular.dto.Message
import java.sql.Connection
import java.sql.DriverManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/api")
class HealthController {

    @Value("\${spring.datasource.url}") lateinit var dataSourceUrl: String

    @GetMapping("/health")
    fun getHealthStatus(): Message {
        var conn: Connection? = null
        try {
            conn = DriverManager.getConnection(dataSourceUrl)
            val statement = conn.createStatement()

            val result = statement.executeQuery("SELECT 1")
            result.next()

            return Message(mapOf("status" to "on-line"), "System is alive", HttpStatus.OK)
        } catch (e: Exception) {
            return Message(
                mapOf("status" to "off-line"),
                "Error connecting to database: ${e.message}",
                HttpStatus.SERVICE_UNAVAILABLE,
            )
        } finally {
            conn?.close()
        }
    }
}
