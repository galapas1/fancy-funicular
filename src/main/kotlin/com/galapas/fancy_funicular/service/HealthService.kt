package com.galapas.fancy_funicular.service

import com.galapas.fancy_funicular.dto.Message
import java.sql.Connection
import java.sql.DriverManager
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class HealthService {
    fun checkHealth(dataSourceUrl: String): Message {
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
