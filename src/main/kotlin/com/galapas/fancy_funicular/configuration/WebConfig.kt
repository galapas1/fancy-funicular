package com.galapas.fancy_funicular.configuration

import com.galapas.fancy_funicular.security.ApiKeyInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    @Value("\${spring.application.apikey}") lateinit var apiKey: String

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(ApiKeyInterceptor(apiKey)).addPathPatterns("/v1/api/preferences/**")
    }
}
