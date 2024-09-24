package com.galapas.fancy_funicular.repository

import com.galapas.fancy_funicular.entity.Preferences
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PreferencesRepository : JpaRepository<Preferences, Long> {
    fun findByEmailAddress(emailAddr: String?): Optional<Preferences>
}
