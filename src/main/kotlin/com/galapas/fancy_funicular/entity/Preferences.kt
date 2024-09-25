package com.galapas.fancy_funicular.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email

@Entity
@Table(name = "preferences")
data class Preferences(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null,
    @field:Email @Column(unique = true) var emailAddress: String = "",
    @Column var tags: List<String> = listOf<String>(),
)
