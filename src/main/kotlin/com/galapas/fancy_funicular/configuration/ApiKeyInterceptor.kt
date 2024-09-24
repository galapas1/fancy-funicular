package com.galapas.fancy_funicular.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.HandlerInterceptor

class ApiKeyInterceptor(val apiKey: String) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val requestApiKey = request.getHeader("X-API-Key")

        if (requestApiKey == apiKey) {
            return true
        } else {
            response.status = 401
            response.writer.write("Unauthorized: Invalid API Key")
            return false
        }
    }

    // Other methods of the interface may be left empty
}
