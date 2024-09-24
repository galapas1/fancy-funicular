package com.galapas.fancy_funicular.entity

import jakarta.persistence.*

@Entity
@Table(name = "preferences", uniqueConstraints = [UniqueConstraint(columnNames = ["emailAddress"])])
data class Preferences(var emailAddr: String? = null) {
    @Id @GeneratedValue var id: Long? = null

    @Column(nullable = false, unique = true) var emailAddress: String? = emailAddr

    @Column var tags: List<String> = listOf<String>()
}
