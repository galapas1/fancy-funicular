package com.galapas.fancy_funicular

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
class FancyFunicularApplication

fun main(args: Array<String>) {
    SpringApplication.run(FancyFunicularApplication::class.java, *args)
}
