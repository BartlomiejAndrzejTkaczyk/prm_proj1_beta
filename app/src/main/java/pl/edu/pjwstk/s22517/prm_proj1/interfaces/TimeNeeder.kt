package pl.edu.pjwstk.s22517.prm_proj1.interfaces

import java.time.LocalDate

interface TimeNeeder {
    fun sendTime(time: LocalDate)
}