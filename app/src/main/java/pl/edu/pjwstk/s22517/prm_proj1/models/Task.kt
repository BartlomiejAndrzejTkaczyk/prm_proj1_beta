package pl.edu.pjwstk.s22517.prm_proj1.models

import java.time.LocalDate

data class Task(
    var id: Int = -1,
    var description: String = "",
    var isDone: Boolean = false,
    var deadline: LocalDate? = null,
)