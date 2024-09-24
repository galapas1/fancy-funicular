package com.galapas.fancy_funicular.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
data class Preferences(
        @TableGenerator(
            name = "prefs_gen",
            table = "ID_GEN",
            pkColumnName = "GEN_NAME",
            valueColumnName = "GEN_VAL",
            pkColumnValue = "pref_gen",
            initialValue = 10, allocationSize = 100)
        @Id @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "prefs_gen")
        var id: Long = 0L,
        @NotNull var emailAddress: String = "",
        var tags: Array<String>
)
